package com.exchange.Money.service.converter;

import com.exchange.Money.model.ConversionForm;

import java.math.BigDecimal;
import java.util.Map;

public interface ConverterService {
    ConversionForm getConversionForm(String customerCurrency, BigDecimal amount, String targetCurrency);
}
