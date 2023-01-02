package com.halil.ozel.covid19stats.screens.home.activity

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.halil.ozel.covid19stats.screens.detail.viewmodel.DetailViewModel
import com.halil.ozel.covid19stats.screens.home.viewmodel.HomeViewModel
import com.halil.ozel.covid19stats.R
import com.halil.ozel.covid19stats.common.models.CountriesResponse
import com.halil.ozel.covid19stats.common.utils.Const.Companion.CASES
import com.halil.ozel.covid19stats.common.utils.Const.Companion.COUNTRY
import com.halil.ozel.covid19stats.common.utils.Const.Companion.DEATHS
import com.halil.ozel.covid19stats.common.utils.Const.Companion.FLAG
import com.halil.ozel.covid19stats.common.utils.Const.Companion.RECOVERED
import com.halil.ozel.covid19stats.common.utils.Const.Companion.TESTS
import com.halil.ozel.covid19stats.common.utils.Const.Companion.TODAY_CASE
import com.halil.ozel.covid19stats.common.utils.Const.Companion.TODAY_DEATH
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
        setView()
        binding.rvCountry.addItemDecoration(
            DividerItemDecoration(
                this,
                LinearLayoutManager.VERTICAL
            )
        )
        binding.rvCountry.layoutManager = LinearLayoutManager(this)
        countryAdapter = CountryAdapter()
        binding.rvCountry.adapter = countryAdapter
        countriesResponseList = ArrayList()

        homeViewModel.countryData.observe(this) {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let { countries ->
                        countryAdapter?.setCountryList(applicationContext, countries)
                    }
                }
                else -> {}
            }
        }

        countryAdapter?.setOnItemClickListener(object : CountryAdapter.OnItemClickListener {
            override fun onItemClick(item: CountriesResponse) {
                usersDetailViewModel._countryLiveData.postValue(item.country)

                Intent(this@MainActivity, DetailActivity::class.java).apply {
                    putExtra(COUNTRY, item.country)
                    putExtra(TODAY_CASE, item.todayCases)
                    putExtra(TODAY_DEATH, item.todayDeaths)
                    putExtra(FLAG, item.countryInfo!!.flag)
                    putExtra(CASES, item.cases)
                    putExtra(DEATHS, item.deaths)
                    putExtra(TESTS, item.tests)
                    putExtra(RECOVERED, item.recovered)
                    startActivity(this)
                }
            }
        })
    }

    private fun setView() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        val searchManager = getSystemService(SEARCH_SERVICE) as SearchManager
        searchView = menu.findItem(R.id.action_search)
            .actionView as SearchView
        searchView?.setSearchableInfo(
            searchManager
                .getSearchableInfo(componentName)
        )
        searchView?.maxWidth = Int.MAX_VALUE
        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                countryAdapter?.filter?.filter(query)
                return false
            }

            override fun onQueryTextChange(query: String): Boolean {
                countryAdapter?.filter?.filter(query)
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

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (!searchView!!.isIconified) {
            searchView?.isIconified = true
            return
        }
        super.onBackPressed()
    }
}