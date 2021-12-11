package com.halil.ozel.covid19stats.repository

import com.halil.ozel.covid19stats.api.ApiHelper
import javax.inject.Inject

/**
 * Created by halilozel1903 on 11.12.2021
 */
class Repository @Inject constructor(private val apiHelper: ApiHelper) {
    suspend fun getCountryList() = apiHelper.getCountryList()
    suspend fun getCountryInfo(country: String) = apiHelper.getCountryInfo(country)
}