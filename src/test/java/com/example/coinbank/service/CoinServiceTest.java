package com.example.coinbank.service;

import com.example.coinbank.dto.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

public class CoinServiceTest {

    CoinService coinService;

    @Mock
    CoinProperties coinProperties;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        when(coinProperties.getQuarter()).thenReturn(Long.valueOf(100));
        when(coinProperties.getDime()).thenReturn(Long.valueOf(100));
        when(coinProperties.getNickel()).thenReturn(Long.valueOf(100));
        when(coinProperties.getPenny()).thenReturn(Long.valueOf(100));


        Coin penny = new Penny(coinProperties);
        Coin nickel = new Nickel(coinProperties, penny);
        Coin dime = new Dime(coinProperties, nickel);
        Coin quarter = new Quarter(coinProperties, dime);
        coinService = new CoinService(quarter);

    }

    @Test
    public void testIsValid() {
        assertTrue(coinService.isValid("2"));
        assertFalse(coinService.isValid(""));
        assertFalse(coinService.isValid("a"));
        assertFalse(coinService.isValid("2.0"));
    }

    @Test
    public void testHasEnough() {
        assertTrue(coinService.hasEnough(BigDecimal.valueOf(20)));
        assertTrue(coinService.hasEnough(BigDecimal.valueOf(41)));
        assertFalse(coinService.hasEnough(BigDecimal.valueOf(42)));
        assertFalse(coinService.hasEnough(BigDecimal.valueOf(200)));
    }
}
