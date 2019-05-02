package com.software.team2.footprint;

public class TradeHistoryItem {

    private String symbol;
    private String company;
    private float bprice;
    private float sprice;
    private int shares;
    private String transactionType;
    private String date;

    public TradeHistoryItem(String symbol, String company, float bprice, float sprice, int shares, String transactionType, String date) {
        this.symbol = symbol;
        this.company = company;
        this.bprice = bprice;
        this.sprice = sprice;
        this.shares = shares;
        this.transactionType = transactionType;
        this.date = date;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getCompany() {
        return company;
    }

    public float getBprice() {
        return bprice;
    }

    public float getSprice() {
        return sprice;
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
}
