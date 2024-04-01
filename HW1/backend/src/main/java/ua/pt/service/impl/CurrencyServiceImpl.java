package ua.pt.service.impl;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ua.pt.service.CurrencyService;
import ua.pt.service.ExternalApiService;

@Service
public class CurrencyServiceImpl implements CurrencyService{
    
    private final ExternalApiService externalApiService;

    @Autowired
    public CurrencyServiceImpl(ExternalApiService externalApiService) {
        this.externalApiService = externalApiService;
    }

    @Override
    public double convertPrice(double price, String currency) {
        double rate = getExchangeRate(currency);
        return Math.round((price * rate) * 100.0) / 100.0;
    }

    public double getExchangeRate(String currency) {
        String result = externalApiService.getCurrencies();
        JSONObject jsonObject = new JSONObject(result);
        return jsonObject.getJSONObject("data").getDouble(currency);
    }
}
