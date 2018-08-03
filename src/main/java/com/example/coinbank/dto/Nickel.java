package com.example.coinbank.dto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("nickel")
public class Nickel extends Coin {


    public Nickel() {
        super();
    }

    @Autowired
    public Nickel(CoinProperties coinProperties, @Qualifier("penny") Coin coin){
        super(coinProperties.getNickel(), coin, CoinType.NICKEL , 20);
    }
}
