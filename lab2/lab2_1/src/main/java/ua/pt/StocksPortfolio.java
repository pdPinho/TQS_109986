package ua.pt;

import java.util.ArrayList;
import java.util.List;

public class StocksPortfolio {
    IStockmarketService stockmarket;
    List<Stock> stocks;

    StocksPortfolio(IStockmarketService stockmarket){
        this.stockmarket = stockmarket;
        this.stocks = new ArrayList<Stock>();
    }

    public void addStock(Stock stock){
        this.stocks.add(stock);
    }

    public double totalValue(){
        double total = 0.0;
        for (Stock stock : stocks)
            total += this.stockmarket.lookUpPrice(stock.getLabel()) * stock.getQuantity();
        
        return total;
    }
}
