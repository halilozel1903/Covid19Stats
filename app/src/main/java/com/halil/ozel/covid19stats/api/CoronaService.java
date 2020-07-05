package com.halil.ozel.covid19stats.api;

import com.halil.ozel.covid19stats.data.AllCountriesResponse;
import com.halil.ozel.covid19stats.data.CountriesResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CoronaService {

    @GET("countries/?sort=country")
    Call<List<CountriesResponse>> getCountries();


    @GET("countries/{country}")
    Call<CountriesResponse> getCountryInfo(

            @Path("country") String country
    );

    @GET("all")
    Call<AllCountriesResponse> getAllCountries();
}
