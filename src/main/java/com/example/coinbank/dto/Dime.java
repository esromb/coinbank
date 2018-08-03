package com.example.coinbank.dto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("dime")
public class Dime extends Coin {

    public Dime(){}

    @Autowired
    public Dime(CoinProperties coinProperties, @Qualifier("nickel") Coin coin){
        super(coinProperties.getDime(), coin, CoinType.DIME, 10);
    }

}
