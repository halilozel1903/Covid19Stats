package com.halil.ozel.covid19stats.ui.activity

import android.app.SearchManager
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.halil.ozel.covid19stats.R
import com.halil.ozel.covid19stats.api.CoronaApi.retrofitInstance
import com.halil.ozel.covid19stats.api.CoronaService
import com.halil.ozel.covid19stats.data.CountriesResponse
import com.halil.ozel.covid19stats.ui.adapter.CountryAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class MainActivity : AppCompatActivity() {

    private var searchView: SearchView? = null
    private var recyclerView: RecyclerView? = null
    private var countryAdapter: CountryAdapter? = null
    private var countriesResponseList: List<CountriesResponse>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.rvCountry)
        recyclerView!!.layoutManager = LinearLayoutManager(this)
        countryAdapter = CountryAdapter()
        recyclerView!!.adapter = countryAdapter
        countriesResponseList = ArrayList()
        val coronaService = retrofitInstance!!.create(CoronaService::class.java)
        val call = coronaService.countries

        call.enqueue(object : Callback<List<CountriesResponse>> {
            override fun onResponse(call: Call<List<CountriesResponse>>, response: Response<List<CountriesResponse>>) {
                countriesResponseList = response.body()
                if (countriesResponseList != null) {
                    for (countriesResponse in countriesResponseList!!) {
                        println("""Country Name : ${countriesResponse.country} - Death Count : ${countriesResponse.deaths}""")
                        countryAdapter!!.setCountryList(applicationContext, countriesResponseList!!)
                    }
                }
            }

            override fun onFailure(call: Call<List<CountriesResponse>>, t: Throwable) {
                Log.d("Error", t.message!!)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        val searchManager = getSystemService(SEARCH_SERVICE) as SearchManager
        searchView = menu.findItem(R.id.action_search)
                .actionView as SearchView
        searchView!!.setSearchableInfo(searchManager
                .getSearchableInfo(componentName))
        searchView!!.maxWidth = Int.MAX_VALUE
        searchView!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                countryAdapter!!.filter.filter(query)
                return false
            }

            override fun onQueryTextChange(query: String): Boolean {
                countryAdapter!!.filter.filter(query)
                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        return if (id == R.id.action_search) {
            true
        } else super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (!searchView!!.isIconified) {
            searchView!!.isIconified = true
            return
        }
        super.onBackPressed()
    }
}