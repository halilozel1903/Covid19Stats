package com.halil.ozel.covid19stats.di

import com.halil.ozel.covid19stats.BuildConfig
import com.halil.ozel.covid19stats.api.ApiHelper
import com.halil.ozel.covid19stats.api.CoronaHelperImpl
import com.halil.ozel.covid19stats.api.CoronaService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Created by halilozel1903 on 11.12.2021
 */
@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Provides
    fun provideApiUrl() = API_URL

    @Singleton
    @Provides
    fun provideOkHttpClient() = if (BuildConfig.DEBUG) {

        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient
            .Builder()
            .addInterceptor(loggingInterceptor)
            .build()

    } else {
        OkHttpClient
            .Builder()
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, API_URL: String): Retrofit =
        Retrofit.Builder()
            .baseUrl(API_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): CoronaService = retrofit.create(
        CoronaService::class.java
    )

    @Singleton
    @Provides
    fun provideApiHelper(apiHelperImpl: CoronaHelperImpl): ApiHelper = apiHelperImpl

    companion object {
        private const val API_URL = "https://disease.sh/v3/covid-19/"
    }
}