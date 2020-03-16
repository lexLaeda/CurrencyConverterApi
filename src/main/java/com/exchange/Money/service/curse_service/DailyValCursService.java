package com.exchange.Money.service.curse_service;

import com.exchange.Money.model.ValCurs;

import java.time.LocalDate;

public interface DailyValCursService {
    ValCurs getValCurs(LocalDate date);
}
