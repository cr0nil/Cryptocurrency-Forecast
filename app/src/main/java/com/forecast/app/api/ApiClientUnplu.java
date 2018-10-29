package com.forecast.app.api;

import com.forecast.app.cryptocurrencyForcast.DashboardActivity;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class ApiClientUnplu {

    private static AsyncHttpClient client = new AsyncHttpClient();
    private static final String BASE_URL = "https://api.unplu.gg/forecast";

    public static String apiKey() {

        return DashboardActivity.apiKey;
    }
    public static void postDate(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.addHeader("x-access-token", apiKey());
        client.addHeader("Content-Type", "application/json");
        client.post(BASE_URL, params, responseHandler);
    }
}
