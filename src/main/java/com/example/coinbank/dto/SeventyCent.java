package com.example.coinbank.dto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("seventy")
public class SeventyCent extends Coin {
    public SeventyCent() {
    }
    @Autowired
    public SeventyCent(CoinProperties coinProperties,  @Qualifier("fifty") Coin coin){
        super(coinProperties.getSeventy(), coin, CoinType.SEVENTY , 1.4285f);
    }
}
