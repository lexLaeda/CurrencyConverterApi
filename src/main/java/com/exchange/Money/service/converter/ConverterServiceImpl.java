package com.exchange.Money.service.converter;

import com.exchange.Money.exception.CurrencyNotFoundException;
import com.exchange.Money.model.ConversionForm;
import com.exchange.Money.model.Currency;
import com.exchange.Money.model.ValCurs;

import com.exchange.Money.service.curse_service.DailyValCursServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ConverterServiceImpl implements ConverterService {

    private final DailyValCursServiceImpl dailyValCursService;
    private static final int SCALE = 4;

    @Override
    public ConversionForm getConversionForm(String customerCurrencyName, BigDecimal customerAmount,
                                            String targetCurrencyName) {
        LocalDate convertTime = LocalDate.now();
        ValCurs dailyCurs = dailyValCursService.getValCurs(convertTime);
        LocalDate timeOfLastUpdate = dailyCurs.getDate();
        return getConversionForm(customerCurrencyName, customerAmount,
                targetCurrencyName, dailyCurs, convertTime, timeOfLastUpdate);
    }

    private ConversionForm getConversionForm(String customerCurrencyName, BigDecimal customerAmount,
                                             String targetCurrencyName, ValCurs dailyCurs,
                                             LocalDate convertTime, LocalDate timeOfLastUpdate) {
        Currency customerCurrency = getCurrency(customerCurrencyName, dailyCurs);
        Currency targetCurrency = getCurrency(targetCurrencyName, dailyCurs);

        return getConversionForm(customerCurrency, customerAmount, targetCurrency, convertTime, timeOfLastUpdate);
    }

    private Currency getCurrency(String name, ValCurs valCurs) {
        return valCurs.getCurrencies().stream()
                .filter(currency -> currency.getCharCode().equals(name))
                .findFirst()
                .orElseThrow(() -> new CurrencyNotFoundException(name));
    }

    private ConversionForm getConversionForm(Currency customerCurrency,
                                             BigDecimal customerAmount, Currency targetCurrency,
                                             LocalDate convertTime, LocalDate timeOfLastUpdate) {
        BigDecimal targetAmount = convertMoney(customerCurrency, customerAmount, targetCurrency);
        return getConversionForm(customerCurrency, customerAmount
                , targetCurrency, targetAmount, convertTime, timeOfLastUpdate);
    }

    private BigDecimal convertMoney(Currency customerCurrency, BigDecimal customerAmount, Currency targetCurrency) {
        BigDecimal cusNominal = new BigDecimal(customerCurrency.getNominal());
        BigDecimal tarNominal = new BigDecimal(targetCurrency.getNominal());
        BigDecimal cusCurse = customerCurrency.getValue();
        BigDecimal tarCurse = targetCurrency.getValue();
        customerAmount = customerAmount.setScale(SCALE,RoundingMode.HALF_UP);
        return convertMoney(cusCurse, cusNominal, customerAmount, tarCurse, tarNominal);
    }

    public BigDecimal convertMoney(BigDecimal cusCurse, BigDecimal cusNominal,
                                    BigDecimal customerAmount, BigDecimal tarCurse, BigDecimal tarNominal) {
        BigDecimal cusKoef = cusCurse.divide(cusNominal,SCALE, RoundingMode.HALF_UP);
        BigDecimal tarKoef = tarCurse.divide(tarNominal,SCALE, RoundingMode.HALF_UP);
        BigDecimal ConvertKoef = cusKoef.divide(tarKoef,SCALE, RoundingMode.HALF_UP);
        return customerAmount.multiply(ConvertKoef).setScale(SCALE,RoundingMode.HALF_UP);
    }


    private ConversionForm getConversionForm(Currency customerCurrency, BigDecimal customerAmount,
                                             Currency targetCurrency, BigDecimal targetAmount,
                                             LocalDate convertTime, LocalDate timeOfLastUpdate) {
        return new ConversionForm.Builder()
                .customerCurrency(customerCurrency)
                .customerAmount(customerAmount)
                .targetCurrency(targetCurrency)
                .targetAmount(targetAmount)
                .convertTime(convertTime)
                .timeOfUpdate(timeOfLastUpdate)
                .build();
    }

}
