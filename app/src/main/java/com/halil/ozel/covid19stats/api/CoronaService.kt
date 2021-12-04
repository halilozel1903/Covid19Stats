package com.halil.ozel.covid19stats.api

import com.halil.ozel.covid19stats.data.CountriesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface CoronaService {

    @GET("countries/?sort=country")
    fun getCountryList():Call<List<CountriesResponse>>

    @GET("countries/{country}")
    fun getCountryInfo(
        @Path("country") country: String?
    ): Call<CountriesResponse?>?

}