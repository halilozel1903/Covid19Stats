package com.halil.ozel.covid19stats.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CoronaApi {

    private static Retrofit retrofit;
    private static final String BASE_URL = "https://corona.lmao.ninja/v2/";

    public static Retrofit getRetrofitInstance() {

        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
