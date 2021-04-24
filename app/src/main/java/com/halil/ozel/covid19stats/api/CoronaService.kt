package com.halil.ozel.covid19stats.api

import com.halil.ozel.covid19stats.data.AllCountriesResponse
import com.halil.ozel.covid19stats.data.CountriesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface CoronaService {
    @get:GET("countries/?sort=country")
    val countries: Call<List<CountriesResponse>>

    @GET("countries/{country}")
    fun getCountryInfo(
            @Path("country") country: String?
    ): Call<CountriesResponse?>?

    @get:GET("all")
    val allCountries: Call<AllCountriesResponse?>?
}