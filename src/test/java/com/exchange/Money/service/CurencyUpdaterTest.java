package com.exchange.Money.service;

import com.exchange.Money.model.ValCurs;
import com.exchange.Money.service.curse_service.CurrencyUpdater;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class CurencyUpdaterTest {

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testGetDailyCurs() {
        ValCurs valCurs = CurrencyUpdater.getDailyCurs();

        assertNotNull(valCurs);

        assertNotNull(valCurs.getName());

        assertNotNull(valCurs.getDate());

        assertNotNull(valCurs.getCurrencies());

        assertEquals(35,valCurs.getCurrencies().size());

        assertEquals("Foreign Currency Market", valCurs.getName());

    }
}