package com.halil.ozel.covid19stats.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.halil.ozel.covid19stats.data.CountriesResponse
import com.halil.ozel.covid19stats.repository.Repository
import com.halil.ozel.covid19stats.utils.NetworkHelper
import com.halil.ozel.covid19stats.utils.Source
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by halilozel1903 on 11.12.2021
 */
@HiltViewModel
class CountryDetailViewModel @Inject constructor(
    private val repository: Repository,
    private val networkHelper: NetworkHelper
) : ViewModel() {

    private val countryData = MutableLiveData<Source<CountriesResponse>>()

    val _countryLiveData = MutableLiveData<String>()
    val countryLiveData: LiveData<String> get() = _countryLiveData

    val id = countryLiveData.value


    override fun getData() {
        viewModelScope.launch {

            countryData.postValue(
                Source.loading(
                    null
                )
            )

            if (networkHelper.isInternetAvailable()) {

                if (id != null) {

                    repository.getCountryInfo(id).let {

                        if (it.isSuccessful) {

                            countryData.postValue(
                                Source.success(
                                    it.body()
                                )
                            )

                        } else {

                            countryData.postValue(
                                Source.error(
                                    it.errorBody().toString(),
                                    null
                                )
                            )
                        }
                    }
                }
            }
        }
    }

}