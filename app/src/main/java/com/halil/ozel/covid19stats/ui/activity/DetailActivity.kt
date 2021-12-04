package com.halil.ozel.covid19stats.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.halil.ozel.covid19stats.databinding.ActivityDetailBinding
import com.squareup.picasso.Picasso

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setIntentInfo()
    }

    private fun setIntentInfo() {
        val countryName = intent.getStringExtra("country")
        val todayCase = intent.getStringExtra("todayCase")
        val todayDeath = intent.getStringExtra("todayDeath")
        val totalCases = intent.getStringExtra("cases")
        val totalDeaths = intent.getStringExtra("deaths")
        val totalTests = intent.getStringExtra("tests")
        val totalRecovered = intent.getStringExtra("recovered")

        binding.tvCountryName.text = countryName
        binding.tvTodayCases.text = todayCase
        binding.tvTodayDeath.text = todayDeath
        binding.tvTotalTests.text = totalTests
        binding.tvTotalCases.text = totalCases
        binding.tvTotalDeaths.text = totalDeaths
        binding.tvTotalRecovered.text = totalRecovered
        Picasso.with(applicationContext).load(intent.getStringExtra("flag"))
            .into(binding.ivCountryPoster)
    }
}