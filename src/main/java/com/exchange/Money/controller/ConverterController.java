package com.exchange.Money.controller;

import com.exchange.Money.model.ConversionForm;
import com.exchange.Money.service.converter.ConverterService;
import com.exchange.Money.service.converter.ConverterServiceImpl;
import lombok.RequiredArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;


@RestController
@RequestMapping("/convert")
@RequiredArgsConstructor
public class ConverterController {
    private final ConverterServiceImpl service;
    private static Logger logger = Logger.getLogger(ConverterController.class);
    @GetMapping("/")
    public ConversionForm getConverted(@RequestParam("c_val") String customerCurrency,
                                       @RequestParam("amount") Double amount,
                                       @RequestParam("t_val") String targetCurrency,
                                       HttpServletRequest request){
        logger.info("Request from " + request.getRemoteAddr());
        ConversionForm conversionForm = service.getConversionForm(
                customerCurrency,new BigDecimal(amount),targetCurrency);
        logger.info(conversionForm.getConvertTime() + " Conversion form sent to user!");
        return conversionForm;
    }

}
