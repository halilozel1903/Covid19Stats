package com.halil.ozel.covid19stats.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object CoronaApi {
    private var retrofit: Retrofit? = null
    private const val BASE_URL = "https://corona.lmao.ninja/v2/"

    @JvmStatic
    val retrofitInstance: Retrofit?
        get() {
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit
        }
}