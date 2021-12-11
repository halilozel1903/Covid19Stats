package com.halil.ozel.covid19stats.api

import com.halil.ozel.covid19stats.data.CountriesResponse
import retrofit2.Response

/**
 * Created by halilozel1903 on 11.12.2021
 */

interface ApiHelper {
    suspend fun getCountryList(): Response<List<CountriesResponse>>
    suspend fun getCountryInfo(country: String): Response<CountriesResponse>
}