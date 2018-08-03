package com.example.coinbank.service;

import com.example.coinbank.dto.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class CoinServiceTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
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

        System.setOut(new PrintStream(outContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
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

    @Test
    public void testDeduct() {
        Map<CoinType, Long> coinTypeCount = new HashMap<>();
        assertTrue(coinService.deduct(BigDecimal.valueOf(20), coinTypeCount));
        assertNotNull(coinTypeCount.get(CoinType.QUARTER));
        assertEquals(coinTypeCount.get(CoinType.QUARTER).longValue(), 80);
        coinTypeCount.clear();
        assertTrue(coinService.deduct(BigDecimal.valueOf(20), coinTypeCount));
        assertEquals(coinTypeCount.get(CoinType.QUARTER).longValue(), 20);
        assertEquals(coinTypeCount.get(CoinType.DIME).longValue(), 100);
        assertEquals(coinTypeCount.get(CoinType.NICKEL).longValue(), 100);
        coinTypeCount.clear();
        assertTrue(coinService.deduct(BigDecimal.valueOf(1), coinTypeCount));
        assertEquals(coinTypeCount.get(CoinType.PENNY).longValue(), 100);

        assertFalse(coinService.hasEnough(BigDecimal.valueOf(20)));

    }

    @Test
    public void testPrint() {
        Map<CoinType, Long> coinTypeCount = new HashMap<>();
        coinTypeCount.put(CoinType.QUARTER, 20l);
        coinService.print(coinTypeCount);
        assertEquals("0.25 coins in 20\n", outContent.toString());
        outContent.reset();
        coinService.print(null);
        assertEquals("", outContent.toString());
    }
}
