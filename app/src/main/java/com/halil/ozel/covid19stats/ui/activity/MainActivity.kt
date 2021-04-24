package com.halil.ozel.covid19stats.ui.activity;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.halil.ozel.covid19stats.R;
import com.halil.ozel.covid19stats.api.CoronaApi;
import com.halil.ozel.covid19stats.api.CoronaService;
import com.halil.ozel.covid19stats.data.CountriesResponse;
import com.halil.ozel.covid19stats.ui.adapter.CountryAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {


    private SearchView searchView;
    private RecyclerView recyclerView;
    private CountryAdapter countryAdapter;
    private List<CountriesResponse> countriesResponseList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        recyclerView = findViewById(R.id.rvCountry);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        countryAdapter = new CountryAdapter();
        recyclerView.setAdapter(countryAdapter);

        countriesResponseList = new ArrayList<>();

        CoronaService coronaService =
                CoronaApi.getRetrofitInstance().create(CoronaService.class);


        Call<List<CountriesResponse>> call = coronaService.getCountries();
        call.enqueue(new Callback<List<CountriesResponse>>() {
            @Override
            public void onResponse(Call<List<CountriesResponse>> call, Response<List<CountriesResponse>> response) {

                countriesResponseList = response.body();


                if (countriesResponseList != null) {
                    for (CountriesResponse countriesResponse : countriesResponseList) {

                        System.out.println("Country Name : " + countriesResponse.getCountry() + " - Death Count : " + countriesResponse.getDeaths() + "\n");

                        countryAdapter.setCountryList(getApplicationContext(), countriesResponseList);


                    }
                }


            }

            @Override
            public void onFailure(Call<List<CountriesResponse>> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                countryAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                countryAdapter.getFilter().filter(query);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
            return;
        }
        super.onBackPressed();
    }


}
