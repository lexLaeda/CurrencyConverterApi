package com.exchange.Money.service.converter;

import com.exchange.Money.model.ConversionForm;
import com.exchange.Money.model.Currency;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class ConverterServiceImplTest {

    @Autowired
    private ConverterServiceImpl service;

    @Test
    public void getConversionForm() {
        String customerValute = "CZK";
        BigDecimal cash = new BigDecimal(2300.412);
        String targetValute = "KRW";

        ConversionForm conversionForm = service.getConversionForm(customerValute,cash,targetValute);

        assertNotNull(conversionForm);
        assertNotNull(conversionForm.getConvertTime());
        assertNotNull(conversionForm.getCustomerAmount());
        assertNotNull(conversionForm.getCustomerCurrency());
        assertNotNull(conversionForm.getTargetAmount());
        assertNotNull(conversionForm.getTargetCurrency());
        assertNotNull(conversionForm.getTimeOfUpdate());

        Currency customerCurrency = conversionForm.getCustomerCurrency();
        Currency targetCurrency = conversionForm.getTargetCurrency();

        assertEquals(customerValute,customerCurrency.getCharCode());
        assertEquals(targetValute,targetCurrency.getCharCode());


    }


}