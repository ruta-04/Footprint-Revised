package com.software.team2.footprint;

public class Stock {

    private String name;
    private String symbol;
    private String price;
    private String change;

    public Stock(String name, String symbol, String price, String change) {
        this.name = name;
        this.change = change;
        this.price = price;
        this.symbol = symbol;
    }

    public Stock() {
        this.name = "";
        this.change = "";
        this.price = "";
        this.symbol = "";
    }

    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getPrice() {
        return price;
    }

    public String getChange() {
        return change;
    }

    public void setName(String na) { this.name=na;}

    public void setSymbol(String sym) { this.symbol=sym;}

    public void setPrice(String pr) { this.price=pr;}

    public void setChange(String ch) { this.change=ch;}

}
