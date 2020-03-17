package com.exchange.Money.service.curse_service;

import com.exchange.Money.exception.DownLoadException;
import com.exchange.Money.model.Currency;
import com.exchange.Money.model.ValCurs;
import com.exchange.Money.service.download.Downloader;
import org.apache.log4j.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.math.BigDecimal;

public class CurrencyUpdater {

    private static String DAILY = "https://www.cbr-xml-daily.ru/daily_utf8.xml";

    private static Logger logger = Logger.getLogger(CurrencyUpdater.class);

    public static ValCurs getDailyCurs() {

        String currenciesFromCB = Downloader.getContent(DAILY);
        StringReader reader = new StringReader(currenciesFromCB);
        return getDailyCurs(reader);
    }

    private static ValCurs getDailyCurs(ValCurs valCurs) {
        valCurs.getCurrencies().add(getRuble(valCurs));
        return valCurs;
    }

    private static ValCurs getDailyCurs(StringReader reader) throws DownLoadException {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(ValCurs.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            ValCurs valCurs = (ValCurs) unmarshaller.unmarshal(reader);
            logger.info("Daily ValCurs downloaded date " + valCurs.getDate());
            return getDailyCurs(valCurs);
        } catch (JAXBException e) {
            logger.error("Can`t parse content \n" + e.getMessage());
        }
        throw new DownLoadException("Information, given by CB was changed");
    }

    private static Currency getRuble(ValCurs valCurs) {

        return new Currency.Builder()
                .addId("R666")
                .addCharCode("RUB")
                .addNumCode(643)
                .addNominal(1)
                .addValue(new BigDecimal(1))
                .addValCurs(valCurs)
                .build();
    }


}
