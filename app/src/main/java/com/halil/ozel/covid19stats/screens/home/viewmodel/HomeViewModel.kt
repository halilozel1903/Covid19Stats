package com.halil.ozel.covid19stats.screens.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.halil.ozel.covid19stats.common.models.CountriesResponse
import com.halil.ozel.covid19stats.business.repository.Repository
import com.halil.ozel.covid19stats.common.utils.NetworkHelper
import com.halil.ozel.covid19stats.common.utils.Source
import com.halil.ozel.covid19stats.presentation.viewmodel.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by halilozel1903 on 11.12.2021
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: Repository,
    private val networkHelper: NetworkHelper
) : ViewModel() {

    private val _countryData = MutableLiveData<Source<List<CountriesResponse>>>()
    val countryData: LiveData<Source<List<CountriesResponse>>> get() = _countryData

    init {
        getData()
    }

    override fun getData() {
        viewModelScope.launch {
            _countryData.postValue(Source.loading(null))

            if (networkHelper.isInternetAvailable()) {
                repository.getCountryList().let { response ->
                    if (response.isSuccessful) {
                        _countryData.postValue(Source.success(response.body()))
                    } else {
                        _countryData.postValue(Source.error(response.errorBody().toString(), null))
                    }
                }
            } else {
                _countryData.postValue(Source.error("Internet connection not found", null))
            }
        }
    }
}