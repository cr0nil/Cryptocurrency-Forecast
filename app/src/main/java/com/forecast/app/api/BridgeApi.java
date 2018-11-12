package com.forecast.app.api;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class BridgeApi {
    private static final String BASE_URL = "http://cr0nil.pythonanywhere.com/todo/api/v1.0/task";

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void getForecast(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), params, responseHandler);

    }
    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;

    }
}
