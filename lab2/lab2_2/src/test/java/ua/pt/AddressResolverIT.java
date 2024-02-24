package ua.pt;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;

import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AddressResolverIT {
    AddressResolverService addressResolverService;

    @BeforeEach
    public void setUp(){
        ISimpleHttpClient iSimpleHttpClient = new TqsBasicHttpClient();
        addressResolverService = new AddressResolverService(iSimpleHttpClient);
    }

    @Test
    public void testGoodCoordinates() throws ParseException, IOException, URISyntaxException, java.text.ParseException {
        Optional<Address> result = addressResolverService.findAddressForLocation(40.63436, -8.65616);
        Address expected = new Address( "Avenida da Universidade", "Aveiro","3810-489", "");

        assertEquals(expected, result.get());
    }

    @Test
    public void testBadCoordinates() throws ParseException, IOException, URISyntaxException, java.text.ParseException {
        Optional<Address> result = addressResolverService.findAddressForLocation(-361, -361);
        
        assertTrue(result.isEmpty());
    }
}