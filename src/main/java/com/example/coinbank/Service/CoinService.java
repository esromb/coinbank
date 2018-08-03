package com.example.coinbank.Service;

import com.example.coinbank.dto.Coin;
import com.example.coinbank.dto.CoinType;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

@Service
public class CoinService {

    private Coin coins;


    public CoinService(@Autowired @Qualifier("quarter") Coin quarter){
        this.coins = quarter;
    }

    public boolean isValid(String bill){
        if (StringUtils.isBlank(bill) || !StringUtils.isNumeric(bill) || bill.contains(".")) {
            return false;
        }
        return true;
    }

    public boolean hasEnough(BigDecimal bill) {
        return coins.hasEnoughCoin(Optional.of(bill));
    }

    public boolean getCoins(BigDecimal bill, Map<CoinType, Long> coinTypeCount){
        return coins.deduct(Optional.of(bill), coinTypeCount);
    }

    public boolean deduct(BigDecimal bill, Map<CoinType,Long> coinTypeCount) {
        return coins.deduct(Optional.of(bill), coinTypeCount);
    }
    public void print(Map<CoinType,Long> coinTypeCount) {
        if (coinTypeCount != null) {
            coinTypeCount.keySet().forEach( coinType -> {
                System.out.println(coinType.getCoinString() + " coins in " + coinTypeCount.get(coinType));
            });
        }
    }
}
