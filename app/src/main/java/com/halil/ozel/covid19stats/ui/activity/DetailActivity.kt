package com.halil.ozel.covid19stats.ui.activity

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.halil.ozel.covid19stats.R
import com.squareup.picasso.Picasso

class DetailActivity : AppCompatActivity() {
    var ivCountryPoster: ImageView? = null
    var tvCountryName: TextView? = null
    var tvTodayCases: TextView? = null
    var tvTodayDeath: TextView? = null
    var tvTotalTests: TextView? = null
    var tvTotalCases: TextView? = null
    var tvTotalDeaths: TextView? = null
    var tvTotalRecovered: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        tvCountryName = findViewById(R.id.tvCountryName)
        tvTodayCases = findViewById(R.id.tvTodayCases)
        tvTodayDeath = findViewById(R.id.tvTodayDeath)
        tvTotalTests = findViewById(R.id.tvTotalTests)
        tvTotalCases = findViewById(R.id.tvTotalCases)
        tvTotalDeaths = findViewById(R.id.tvTotalDeaths)
        tvTotalRecovered = findViewById(R.id.tvTotalRecovered)
        ivCountryPoster = findViewById(R.id.ivCountryPoster)
        val countryName = intent.getStringExtra("country")
        val todayCase = intent.getStringExtra("todayCase")
        val todayDeath = intent.getStringExtra("todayDeath")
        val totalCases = intent.getStringExtra("cases")
        val totalDeaths = intent.getStringExtra("deaths")
        val totalTests = intent.getStringExtra("tests")
        val totalRecovered = intent.getStringExtra("recovered")
        tvCountryName!!.text = countryName
        tvTodayCases!!.text = todayCase
        tvTodayDeath!!.text = todayDeath
        tvTotalTests!!.text = totalTests
        tvTotalCases!!.text = totalCases
        tvTotalDeaths!!.text = totalDeaths
        tvTotalRecovered!!.text = totalRecovered
        Picasso.with(applicationContext).load(intent.getStringExtra("flag"))
                .into(ivCountryPoster)
    }
}