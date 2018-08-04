package com.example.coinbank.dto;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

public abstract class Coin {

    private BigDecimal numberOfCoins;

    private Coin coin;

    private CoinType coinType;


    private float coinMultiplier;


    public Coin(){}

    public Coin(long numberOfCoins, Coin coin, CoinType coinType, float coinMultiplier) {
        this.numberOfCoins = BigDecimal.valueOf(numberOfCoins);
        this.coin = coin;
        this.coinType = coinType;
        this.coinMultiplier = coinMultiplier;
    }

    public void setNumberOfCoins(BigDecimal numberOfCoins) {
        this.numberOfCoins = numberOfCoins;
    }

    public BigDecimal getNumberOfCoins() {
        return numberOfCoins;
    }

    /**
     * The method check if there is enough Coin to convert
     * @param bill the amount you want to convert to Coin
     * @return boolean
     */
    public boolean hasEnoughCoin(Optional<BigDecimal> bill) {

        if (bill.isPresent()) {
            if (this.getNumberOfCoins().subtract(bill.get().multiply(BigDecimal.valueOf(this.coinMultiplier))).intValue() >= 0) {
                return true;
            }
            if (this.coin != null) {
                BigDecimal amountToDeductFromThisCoinSet = BigDecimal.valueOf(bill.get().multiply(BigDecimal.valueOf(this.coinMultiplier)).longValue());
                BigDecimal remaining = this.getNumberOfCoins()
                        .subtract(amountToDeductFromThisCoinSet).abs();
                return this.coin.hasEnoughCoin(Optional.of(remaining.divide(BigDecimal.valueOf(BigDecimal.valueOf(this.coinMultiplier).longValue()))));
            }
        }
        return false;
    }

    /**
     * The method return remaining bill amount after convertion
     * @param bill- bill amount to convert
     * @return BigDecimal value remaining after convertion
     */
    public Optional<BigDecimal> remaining(Optional<BigDecimal> bill) {
        if (bill.isPresent()) {
            long remaining = this.getNumberOfCoins().subtract(bill.get().multiply(BigDecimal.valueOf(this.coinMultiplier))).longValue();
            if (remaining < 0){
                return Optional.of(BigDecimal.valueOf(remaining).abs());
            }
            return Optional.of(BigDecimal.ZERO);
        }
        return Optional.empty();
    }

    /**
     * Deduct bill amount from coin
     * @param bill
     * @param coinTypeCount
     * @return boolean whether deduction is succeed or not
     */
    public boolean deduct(Optional<BigDecimal> bill, Map<CoinType, Long> coinTypeCount) {
        if (bill.isPresent() && bill.get().intValue() > 0 && this.hasEnoughCoin(bill)) {
            Optional<BigDecimal> remaining = this.remaining(bill);
            if (remaining.isPresent() && remaining.get().intValue() >= 0) {
                long amountToDeduct = Math.min(bill.get().multiply(BigDecimal.valueOf(this.coinMultiplier)).longValue(),
                        this.getNumberOfCoins().longValue());
                this.setNumberOfCoins(
                        this.getNumberOfCoins()
                                .subtract(BigDecimal.valueOf(
                                        amountToDeduct)));
                coinTypeCount.put(this.coinType, amountToDeduct);
                if (this.coin != null && remaining.get().intValue() > 0) {
                    BigDecimal remainingTobeDeductedFromOtherCoin = remaining.get().divide(BigDecimal.valueOf(BigDecimal.valueOf(this.coinMultiplier).longValue()));
                    return this.coin.deduct(Optional.of(remainingTobeDeductedFromOtherCoin), coinTypeCount);
                }
                return true;

            }
        }
        return false;
    }
}
