package com.exchange.Money.controller;

import com.exchange.Money.model.ConversionForm;
import com.exchange.Money.service.converter.ConverterService;
import com.exchange.Money.service.converter.ConverterServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;


@RestController
@RequestMapping("/convert")
@RequiredArgsConstructor
public class ConverterController {
    private final ConverterServiceImpl service;
    @GetMapping("/")
    public ConversionForm getConverted(@RequestParam("c_val") String customerCurrency,
                                       @RequestParam("amount") Double amount,
                                       @RequestParam("t_val") String targetCurrency){

        return service.getConversionForm(customerCurrency,new BigDecimal(amount),targetCurrency);
    }

}
