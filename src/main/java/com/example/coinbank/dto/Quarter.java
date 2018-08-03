package com.example.coinbank.dto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("quarter")
public class Quarter extends Coin {

    public Quarter(){}
    @Autowired
    public Quarter(CoinProperties coinProperties,  @Qualifier("dime") Coin coin){
        super(coinProperties.getQuarter(), coin, CoinType.QUARTER , 4);
    }

}
