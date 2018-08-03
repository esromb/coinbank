package com.example.coinbank.dto;

public enum CoinType {
    QUARTER("0.25"), DIME("0.10"), NICKEL("0.05"), PENNY("0.01");

    String coinString;
    CoinType(String coinString) {
        this.coinString = coinString;
    }
    public String getCoinString(){
        return this.coinString;
    }
}
