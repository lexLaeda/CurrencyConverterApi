package com.exchange.Money.adapter;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.Assert.*;

public class LocalDateAdapterTest {
    private LocalDateAdapter localDateAdapter;

    @Before
    public void setUp() throws Exception {
        localDateAdapter = new LocalDateAdapter();
    }

    @Test
    public void testUnmarshal() {
        String dateStr = "12.12.2020";
        try {
            LocalDate localDate = localDateAdapter.unmarshal(dateStr);
            assertNotNull(localDate);
            assertEquals(Month.DECEMBER,localDate.getMonth());
            assertEquals(2020,localDate.getYear());
            assertEquals(12,localDate.getDayOfMonth());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testBadData(){
        String dateStr = "12a12a2020";
        try {
            LocalDate localDate = localDateAdapter.unmarshal(dateStr);
            fail();
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("Text '12a12a2020' could not be parsed at index 2"));
        }
    }
    @Test
    public void testMarshal() {
        LocalDate localDate = LocalDate.MAX;

        String localDateStr = localDate.toString();

        assertEquals("+999999999-12-31",localDateStr);
    }
}