package model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MStocks {
    private String symbol;
    private String country;
    private String name;
    private String currency;
    private String exchange;
    private String type;

    public MStocks(String symbol, String country, String name, String currency, String exchange, String type) {
        this.symbol = symbol;
        this.country = country;
        this.name = name;
        this.currency = currency;
        this.exchange = exchange;
        this.type = type;
    }

    public static List<MStocks> filterByCurrency(List<MStocks> stocksList, String currency){
        List<MStocks> newStocksList = new ArrayList<MStocks>();
        for (MStocks stock : stocksList){
            if(stock.getCurrency().equals(currency)){
                newStocksList.add(stock);
            }
        }
        return newStocksList.stream().limit(10).collect(Collectors.toList());
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
