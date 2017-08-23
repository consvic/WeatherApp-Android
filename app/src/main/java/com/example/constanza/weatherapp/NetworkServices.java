package com.example.constanza.weatherapp;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Constanza on 22/08/2017.
 */

public class NetworkServices {
    private OkHttpClient client;
    public NetworkServices() {
        client = new OkHttpClient();
    }

    public String requestWeather (String url) throws IOException {
        Request request = new Request.Builder().url(url).build();
        System.out.println("req" +request);
        Response res = client.newCall(request).execute();
        System.out.println("res: "+res);
        return res.body().string();
    }

}
