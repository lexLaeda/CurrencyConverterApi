package com.exchange.Money.service.curse_service;

import com.exchange.Money.model.ValCurs;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class DailyValCursServiceImplTest {
    @Autowired
    private DailyValCursServiceImpl dailyValCursService;
    @Test
    public void getValCurs() {
        LocalDate now = LocalDate.now();
        ValCurs daily = dailyValCursService.getValCurs(now);

        assertNotNull(daily);
        assertNotNull(daily.getDate());
        assertNotNull(daily.getName());
        assertNotNull(daily.getCurrencies());
        assertNotNull(daily.getId());

        assertEquals(daily.getDate().getMonth(),now.getMonth());

        assertEquals(35,daily.getCurrencies().size());

    }
}