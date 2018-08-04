package com.example.coinbank.dto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("fifty")
public class FiftyCent extends Coin  {
    public FiftyCent(){}
    @Autowired
    public FiftyCent(CoinProperties coinProperties,  @Qualifier("quarter") Coin coin){
        super(coinProperties.getFifty(), coin, CoinType.FIFTY , 2);
    }
}
