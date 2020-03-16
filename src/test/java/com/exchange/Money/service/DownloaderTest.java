package com.exchange.Money.service;


import com.exchange.Money.exception.DownLoadException;
import com.exchange.Money.service.download.Downloader;
import org.junit.Test;

import static org.junit.Assert.*;

public class DownloaderTest {


    @Test
    public void testGetContent() {
        String DAILY = "https://www.cbr-xml-daily.ru/daily_utf8.xml";

        String downloaded = Downloader.getContent(DAILY);

        assertTrue(downloaded.contains("Foreign Currency Market"));
    }
    @Test(expected = DownLoadException.class)
    public void testGetFakeURL(){
        String DAILY = "https://www.badWay.ru/PPPPPaily_utf8.xml";
        String downloaded = Downloader.getContent(DAILY);
    }

}