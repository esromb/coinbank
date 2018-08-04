package com.example.coinbank.dto;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("seventyone")
public class SeventyOneCent  extends  Coin {
    public SeventyOneCent() {
    }

    public SeventyOneCent(CoinProperties coinProperties,  @Qualifier("seventy") Coin coin){
        super(coinProperties.getSeventyOne(), coin, CoinType.SEVENTY_ONE, 1.4084f);
    }
}
