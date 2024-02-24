package ua.pt;

/*
 * taken from https://gitlab.com/ico_gl/ua_tqs_gs20
 */

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.Formatter;
import java.util.Locale;
import java.util.Optional;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.apache.http.client.utils.URIBuilder;
import org.json.simple.JSONArray;


public class AddressResolverService {
    private ISimpleHttpClient iSimpleHttpClient;

    AddressResolverService(ISimpleHttpClient iSimpleHttpClient){
        this.iSimpleHttpClient = iSimpleHttpClient;    
    }

    private static String prepareUriForRemoteEndpoint(double latitude, double longitude) throws URISyntaxException{
        String apiKey = ConfigUtils.getPropertyFromConfig("mapquest_key");

        URIBuilder uriBuilder = new URIBuilder("https://www.mapquestapi.com/geocoding/v1/reverse");
        uriBuilder.addParameter("key", apiKey);
        uriBuilder.addParameter("location", (new Formatter()).format(Locale.US, "%.5f,%.5f", latitude, longitude).toString());
        uriBuilder.addParameter("outFormat", "json");
        uriBuilder.addParameter("thumbMaps", "false");
        return uriBuilder.build().toString();
    }

    public Optional<Address> findAddressForLocation(double latitude, double longitude) throws URISyntaxException, IOException, ParseException, org.json.simple.parser.ParseException {
        String endpointUri = prepareUriForRemoteEndpoint(latitude, longitude);
        String response = iSimpleHttpClient.doHttpGet(endpointUri);

        // get parts from response till reaching the address
        JSONObject obj = (JSONObject) new JSONParser().parse(response);
        obj = (JSONObject) ((JSONArray) obj.get("results")).get(0);
        if (((JSONArray) obj.get("locations")).isEmpty()) {
            return Optional.empty();
        } else {
            JSONObject address = (JSONObject) ((JSONArray) obj.get("locations")).get(0);
            String road = (String) address.get("street");
            String city = (String) address.get("adminArea5");
            String zip = (String) address.get("postalCode");
            return Optional.of(new Address(road, city, zip, ""));
        }
    }
}
