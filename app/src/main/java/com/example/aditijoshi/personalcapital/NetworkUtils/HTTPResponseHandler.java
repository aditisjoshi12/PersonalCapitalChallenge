package com.example.aditijoshi.personalcapital.NetworkUtils;

/**
 * Created by aditijoshi on 9/24/17.
 */

import okhttp3.Response;

public class HTTPResponseHandler {

    public void onSuccess(Response root) {
    }

    public void onError(Response response, Throwable e) {
        e.printStackTrace();
    }
}
