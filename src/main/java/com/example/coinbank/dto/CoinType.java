package com.example.coinbank.dto;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

public enum CoinType {

    SEVENTY_ONE("0.71", 0.71f, "seventyOne"),
    SEVENTY("0.70", 0.70f, "seventy"),
    FIFTY("0.50", 0.50f, "fifty"),
    QUARTER("0.25", 0.25f, "quarter"),
    DIME("0.10", 0.10f, "dime"),
    NICKEL("0.05", 0.05f, "nickel"),
    PENNY("0.01", 0.01f, "penny");

    String coinString;
    Float value;
    /**
     * will be used to get coins quantity from configurable property object
     */
    String propertyName;
    CoinType(String coinString, float value, String propertyName) {
        this.coinString = coinString;
        this.value = value;
        this.propertyName = propertyName;
    }
    public String getCoinString(){
        return this.coinString;
    }

    public float getValue() {
        return value;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public static Set<CoinType> getSortedCoins(){

        Set<CoinType> sortedCoinTypes = new TreeSet<>(new Comparator<CoinType>() {
            @Override
            public int compare(CoinType type1, CoinType type2) {
                return type2.value.compareTo(type1.value);
            }
        });
        sortedCoinTypes.addAll(Arrays.asList(CoinType.values()));
        return sortedCoinTypes;
    }
}
