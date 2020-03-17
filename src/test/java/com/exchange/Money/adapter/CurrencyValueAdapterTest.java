package com.exchange.Money.adapter;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class CurrencyValueAdapterTest {

    private CurrencyValueAdapter currencyValueAdapter;
    private BigDecimal bigAmount;
    @Before
    public void setUp() {
        currencyValueAdapter = new CurrencyValueAdapter();
        bigAmount = new BigDecimal(10002.2122);
    }

    @Test
    public void testUnmarshal() {

        String cash = "10002.2122";
        String cash2 = "10002,2122";


        try {
            BigDecimal value = currencyValueAdapter.unmarshal(cash);
            BigDecimal value2 = currencyValueAdapter.unmarshal(cash2);

            assertNotNull(value);
            assertNotNull(value2);
            assertEquals(bigAmount, value);
            assertEquals(bigAmount, value2);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testBadDataUnmarshal() {
        String cash = "1000sad122";

        try {
            BigDecimal value = currencyValueAdapter.unmarshal(cash);
        } catch (Exception e) {
            assertEquals("For input string: \"1000sad122\"",e.getMessage());
        }
    }
    @Test
    public void testMarshal() {

        try {
            String cash = currencyValueAdapter.marshal(bigAmount);
            assertTrue(cash.contains("10002.212"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}