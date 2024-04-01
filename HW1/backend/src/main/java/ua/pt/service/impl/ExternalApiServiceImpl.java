package ua.pt.service.impl;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import ua.pt.service.ExternalApiService;

@Service
public class ExternalApiServiceImpl implements ExternalApiService {
    
    @Cacheable("currencies")
    @Override
    public String getCurrencies() {
        final String uri = "https://api.freecurrencyapi.com/v1/latest?apikey=fca_live_U6JPC3Onqk5qw1MfplS81oCTslg12bcYdGR5RqbR";

        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(uri, String.class);
    }
}
