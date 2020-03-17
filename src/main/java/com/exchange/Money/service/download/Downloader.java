package com.exchange.Money.service.download;

import com.exchange.Money.exception.DownLoadException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.log4j.*;


import java.io.IOException;


public class Downloader {
    private static final String DOWNLOADEXCEPTION = "Server is unavailable";

    private static final Logger logger = Logger.getLogger(Downloader.class);

    public static String getContent(String url) throws DownLoadException {

        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            Response response = client.newCall(request).execute();
            logger.info("CB api call requested URl: " + url);
            return response.body().string();
        } catch (IOException e) {
            logger.error(e);
            throw new DownLoadException(DOWNLOADEXCEPTION);
        }
    }
}
