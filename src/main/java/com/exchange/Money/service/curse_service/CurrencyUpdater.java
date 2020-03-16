package com.exchange.Money.service.curse_service;

import com.exchange.Money.exception.DownLoadException;
import com.exchange.Money.model.ValCurs;
import com.exchange.Money.service.download.Downloader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;

public class CurrencyUpdater {

    private static String DAILY = "https://www.cbr-xml-daily.ru/daily_utf8.xml";

    public static ValCurs getDailyCurs() {

        String currenciesFromCB = Downloader.getContent(DAILY);
        StringReader reader = new StringReader(currenciesFromCB);
        return getDailyCurs(reader);
    }

    private static ValCurs getDailyCurs(StringReader reader) throws DownLoadException {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(ValCurs.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            ValCurs valCurs = (ValCurs) unmarshaller.unmarshal(reader);
            return valCurs;
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        throw new DownLoadException("Information, given by CB was changed");
    }


}
