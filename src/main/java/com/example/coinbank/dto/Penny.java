package com.example.coinbank.dto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component("penny")
public class Penny extends Coin {

    public Penny(){}


    @Autowired
    public Penny(CoinProperties coinProperties){
        super(coinProperties.getPenny(), null, CoinType.PENNY, 100);
    }
}
