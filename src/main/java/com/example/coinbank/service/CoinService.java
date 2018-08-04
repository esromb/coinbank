package com.example.coinbank.service;

import ch.qos.logback.core.encoder.EchoEncoder;
import com.example.coinbank.dto.Coin;
import com.example.coinbank.dto.CoinProperties;
import com.example.coinbank.dto.CoinType;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Service
public class CoinService {

    private Coin coins;

    private Set<CoinType> coinTypes;

    private Map<CoinType, Integer> coinQuantities = new HashMap<>();


    public CoinService(@Autowired CoinProperties coinProperties){
        this.coinTypes = CoinType.getSortedCoins();
        coinQuantities.clear();
        if (this.coinTypes != null && coinProperties != null){
            this.coinTypes.forEach( coinType -> {
                try {
                    Long quantity = (Long) new PropertyDescriptor(coinType.getPropertyName(), CoinProperties.class).getReadMethod().invoke(coinProperties);
                    if (quantity != null) {
                        coinQuantities.put(coinType, quantity.intValue()) ;
                    } else {
                        coinQuantities.put(coinType, 0) ;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    coinQuantities.put(coinType, 0) ;
                }
            });

        }
    }

    public boolean isValid(String bill){
        if (StringUtils.isBlank(bill) || !StringUtils.isNumeric(bill) || bill.contains(".")) {
            return false;
        }
        return true;
    }

    public boolean hasEnough(BigDecimal bill) {
        if (bill != null) {
            BigDecimal remainingBalance = BigDecimal.valueOf(bill.longValue());
            for (CoinType coinType : this.coinTypes) {
                if (remainingBalance.doubleValue() < coinType.getValue()) {
                    continue;
                }
                Integer availableQuantity = this.coinQuantities.get(coinType);
                if (availableQuantity != null && availableQuantity > 0) {
                    double quantityNeededFromCurrentCoinType = Math.floorDiv((int) (remainingBalance.doubleValue() * 100), (int) (coinType.getValue() * 100));
                    double amountToDeduct = BigDecimal.valueOf(quantityNeededFromCurrentCoinType * coinType.getValue()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                    double currentTotalBalance = BigDecimal.valueOf(availableQuantity * coinType.getValue()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                    if (availableQuantity == quantityNeededFromCurrentCoinType || amountToDeduct == currentTotalBalance ) {
                        return true;
                    } else if (availableQuantity >= quantityNeededFromCurrentCoinType && remainingBalance.doubleValue() - amountToDeduct > 0) {
                        remainingBalance = remainingBalance.subtract(BigDecimal.valueOf(amountToDeduct));
                    } else if ( availableQuantity >= quantityNeededFromCurrentCoinType && (remainingBalance.doubleValue() - amountToDeduct == 0)) {
                        return true;
                    } else if (availableQuantity < quantityNeededFromCurrentCoinType) {
                        remainingBalance = remainingBalance.subtract(BigDecimal.valueOf(currentTotalBalance));
                    }
                }

            }
            if (remainingBalance.equals(BigDecimal.ZERO)) {
                return true;
            }
        }
        return false;
    }
    public boolean deduct(BigDecimal bill, Map<CoinType,Long> coinTypeCount) {
        if (bill != null) {
            BigDecimal remainingBalance = BigDecimal.valueOf(bill.longValue());
            for (CoinType coinType : this.coinTypes) {
                if (remainingBalance.doubleValue() < coinType.getValue()) {
                    continue;
                }
                Integer availableQuantity = this.coinQuantities.get(coinType);
                if (availableQuantity != null && availableQuantity > 0) {
                    double quantityNeededFromCurrentCoinType = Math.floorDiv((int) (remainingBalance.doubleValue() * 100), (int) (coinType.getValue() * 100));
                    double amountToDeduct = BigDecimal.valueOf(quantityNeededFromCurrentCoinType * coinType.getValue()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                    double currentTotalBalance = BigDecimal.valueOf(availableQuantity * coinType.getValue()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

                    if (availableQuantity == quantityNeededFromCurrentCoinType || amountToDeduct == currentTotalBalance) {
                        this.coinQuantities.put(coinType, 0);
                        coinTypeCount.put(coinType, availableQuantity.longValue());
                        return true;
                    } else if (availableQuantity >= quantityNeededFromCurrentCoinType && (remainingBalance.doubleValue() - amountToDeduct > 0)) {
                        coinTypeCount.put(coinType, BigDecimal.valueOf(quantityNeededFromCurrentCoinType).longValue());
                        this.coinQuantities.put(coinType, Double.valueOf(availableQuantity - quantityNeededFromCurrentCoinType).intValue());
                        remainingBalance = remainingBalance.subtract(BigDecimal.valueOf(amountToDeduct));
                    } else if (availableQuantity >= quantityNeededFromCurrentCoinType && (remainingBalance.doubleValue() - amountToDeduct == 0)) {
                        this.coinQuantities.put(coinType, Double.valueOf(availableQuantity - quantityNeededFromCurrentCoinType).intValue());
                        coinTypeCount.put(coinType, BigDecimal.valueOf(quantityNeededFromCurrentCoinType).longValue());
                        return true;
                    } else if (availableQuantity <= quantityNeededFromCurrentCoinType) {
                        this.coinQuantities.put(coinType, 0);
                        coinTypeCount.put(coinType, availableQuantity.longValue());
                        remainingBalance = remainingBalance.subtract(BigDecimal.valueOf(currentTotalBalance));
                    }
                }

            }
            if (remainingBalance.equals(BigDecimal.ZERO)) {
                return true;
            }
        }
        return false;
    }
    public void print(Map<CoinType,Long> coinTypeCount) {
        if (coinTypeCount != null) {
            coinTypeCount.keySet().forEach( coinType -> {
                System.out.println(coinType.getCoinString() + " coins in " + coinTypeCount.get(coinType));
            });
        }
    }
}
