package com.example.creately.questions;

import com.squareup.okhttp.OkHttpClient;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * Created by rahul on 08/12/16.
 */

public class ApiGenerator {

    public static final String BASE_URL =  "https://api.stackexchange.com/2.2/";
    public static final String api_key= "bEwPjSnm88FRqszCjGTPBg((";



    public  static <S> S createService(Class<S> serviceClass)
    {

        RequestInterceptor requestInterceptor=new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                request.addHeader("Accept", "application/json");
                request.addQueryParam("api_key",api_key);
            }
        };

        OkHttpClient okHttpClient = new OkHttpClient();

        RestAdapter.Builder builder = new RestAdapter.Builder()
                .setEndpoint(BASE_URL)
                .setClient(new OkClient(okHttpClient))
                .setLogLevel(RestAdapter.LogLevel.FULL);
        builder.setRequestInterceptor(requestInterceptor);
        RestAdapter adapter = builder.build();

        return adapter.create(serviceClass);

    }

}

