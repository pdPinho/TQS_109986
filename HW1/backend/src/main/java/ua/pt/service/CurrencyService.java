package ua.pt.service;


public interface CurrencyService {
    public double convertPrice(double price, String currency);
    public double getExchangeRate(String currency);
}
