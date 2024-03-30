package ua.pt.Service;


public interface CurrencyService {
    public double convertPrice(double price, String currency);
    public double getExchangeRate(String currency);
}
