package ua.pt.Service.Impl;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import ua.pt.Service.CurrencyService;

@Service
public class CurrencyServiceImpl implements CurrencyService{
    
    @Override
    public double convertPrice(double price, String currency) {
        double rate = getExchangeRate(currency);
        return price * rate;
    }

    @Cacheable(cacheNames="currencies", key="#currency")
    @Override
    public double getExchangeRate(String currency) {
        final String uri = "https://api.freecurrencyapi.com/v1/latest?apikey=fca_live_U6JPC3Onqk5qw1MfplS81oCTslg12bcYdGR5RqbR";

        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri, String.class);

        System.out.println(result);
        return 0;
    }
}
