package com.example.aditijoshi.personalcapital.NetworkUtils;


import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import de.greenrobot.event.EventBus;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpRequest {

    public static final MediaType TYPE_FORM_ENCODED = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");


    private static ExecutorService executorService;

    private final static int threadsPoolNumber = 10;
    private static HttpRequest mInstance;

    public HttpRequest() {
        executorService = Executors.newFixedThreadPool(threadsPoolNumber);
    }

    public static HttpRequest getInstance() {
        if (mInstance == null) {
            mInstance = new HttpRequest();
        }
        return mInstance;
    }


    public void post(String url, int localTimeout, HTTPResponseHandler handler) {
        makeRequest("get", url, localTimeout, null, handler);
    }

    /**
     * @param method       get/post
     * @param url          full URL
     * @param localTimeout timeout in ms
     * @param requestBody  optional Request Body
     * @param handler      handler for results
     */
    // ONLY OPTIMIZED FOR GET REQUESTS, IDEALLY call should be based on method parameter
    private void makeRequest(final String method, final String url, final int localTimeout,
                             final String requestBody, final HTTPResponseHandler handler) {


        executorService.submit(new Callable<Void>() {
            @Override
            public Void call() {
                if (Thread.interrupted()) {
                    return null;
                }

                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(localTimeout, TimeUnit.SECONDS)
                        .writeTimeout(localTimeout, TimeUnit.SECONDS)
                        .readTimeout(localTimeout, TimeUnit.SECONDS)
                        .build();

                Request.Builder reqBuilder = new Request.Builder();

                RequestBody body = RequestBody.create(TYPE_FORM_ENCODED, "");
                Request request = reqBuilder.url(url).post(body).build();

                Response response = null;
                try {
                    response = client.newCall(request).execute();

                } catch (IOException e) {
                    if (handler != null) {
                        // handling no internet error cases here in a geenric location instead of repeating the code at every API call handler
                        //in case of no internet, the handler is null
                        if (e.getClass().getSimpleName().equals("UnknownHostException")) {
                            EventBus.getDefault().post(new NoNetworkConnectionEvent());
                        }
                        handler.onError(response, e);

                    }

                    e.printStackTrace();
                }

                if (handler != null) {
                    handler.onSuccess(response);
                }

                return null;
            }
        });

    }











}
