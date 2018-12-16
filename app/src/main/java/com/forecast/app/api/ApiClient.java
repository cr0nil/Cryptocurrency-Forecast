package com.forecast.app.api;

import com.loopj.android.http.*;

public class ApiClient {
    private static final String BASE_URL = "https://www.bitmarket.pl/";

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void getCryptocurrency(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), params, responseHandler);

    }
    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;

    }

}

