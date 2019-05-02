package com.software.team2.footprint;

public class TradeHistoryItem {

    private String symbol;
    private String company;
    private float eachPrice;
    private float price;
    private int shares;
    private String transactionType;
    private String date;
    private float totalPrice;

    public TradeHistoryItem(String symbol, String company, float eachPrice, float price, float totalPrice, int shares, String transactionType, String date) {
        this.symbol = symbol;
        this.company = company;
        this.eachPrice = eachPrice;
        this.price = price;
        this.shares = shares;
        this.transactionType = transactionType;
        this.date = date;
        this.totalPrice = totalPrice;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getCompany() {
        return company;
    }

    public float getEachPrice() {
        return eachPrice;
    }

    public float getPrice() {
        return price;
    }

    public int getShares() {
        return shares;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public String getDate() {
        return date;
    }

    public float getTotalPrice() {
        return totalPrice;
    }
}
