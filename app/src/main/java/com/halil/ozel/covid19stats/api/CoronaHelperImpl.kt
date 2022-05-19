package com.halil.ozel.covid19stats.api

import com.halil.ozel.covid19stats.common.models.CountriesResponse
import retrofit2.Response
import javax.inject.Inject

/**
 * Created by halilozel1903 on 11.12.2021
 */
class CoronaHelperImpl @Inject constructor(private val service: CoronaService) : ApiHelper {
    override suspend fun getCountryList(): Response<List<CountriesResponse>> =
        service.getCountryList()

    override suspend fun getCountryInfo(country: String): Response<CountriesResponse> =
        service.getCountryInfo(country)
}