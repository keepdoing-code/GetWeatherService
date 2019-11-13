package ru.keepdoing.service;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class ExternalWeatherHttpService {

    private static HttpClient httpClient = HttpClientBuilder.create().build();

    public static String getResponse(String url) throws IOException {
        HttpResponse response = httpClient.execute(new HttpGet(url));
        if (response.getStatusLine().getStatusCode() != 200) {
            throw new IOException(response.getStatusLine().getReasonPhrase());
        }
        return EntityUtils.toString(response.getEntity());
    }
}
