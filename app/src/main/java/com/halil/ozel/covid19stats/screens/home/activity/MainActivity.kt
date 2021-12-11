package com.halil.ozel.covid19stats.screens.home.activity

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.halil.ozel.covid19stats.screens.detail.viewmodel.DetailViewModel
import com.halil.ozel.covid19stats.screens.home.viewmodel.HomeViewModel
import com.halil.ozel.covid19stats.R
import com.halil.ozel.covid19stats.common.models.CountriesResponse
import com.halil.ozel.covid19stats.databinding.ActivityMainBinding
import com.halil.ozel.covid19stats.screens.detail.activity.DetailActivity
import com.halil.ozel.covid19stats.screens.home.adapter.CountryAdapter
import com.halil.ozel.covid19stats.common.utils.Status
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val homeViewModel: HomeViewModel by viewModels()
    private val usersDetailViewModel: DetailViewModel by viewModels()
    private var searchView: SearchView? = null
    private var countryAdapter: CountryAdapter? = null
    private var countriesResponseList: List<CountriesResponse>? = null
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.rvCountry.layoutManager = LinearLayoutManager(this)
        countryAdapter = CountryAdapter()
        binding.rvCountry.adapter = countryAdapter
        countriesResponseList = ArrayList()

        homeViewModel.countryData.observe(this, {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let { countries ->
                        countryAdapter!!.setCountryList(applicationContext, countries)

                    }
                }
            }
        })

        countryAdapter!!.setOnItemClickListener(object : CountryAdapter.OnItemClickListener {
            override fun onItemClick(item: CountriesResponse) {
                println("Country: ${item.country}")
                usersDetailViewModel._countryLiveData.postValue(item.country)

                val intent = Intent(this@MainActivity, DetailActivity::class.java)
                intent.putExtra("country", item.country)
                intent.putExtra("todayCase", item.todayCases)
                intent.putExtra("todayDeath", item.todayDeaths)
                intent.putExtra("flag", item.countryInfo!!.flag)
                intent.putExtra("cases", item.cases)
                intent.putExtra("deaths", item.deaths)
                intent.putExtra("tests", item.tests)
                intent.putExtra("recovered", item.recovered)
                startActivity(intent)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        val searchManager = getSystemService(SEARCH_SERVICE) as SearchManager
        searchView = menu.findItem(R.id.action_search)
            .actionView as SearchView
        searchView!!.setSearchableInfo(
            searchManager
                .getSearchableInfo(componentName)
        )
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