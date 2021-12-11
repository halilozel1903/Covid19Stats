package com.halil.ozel.covid19stats

import android.app.Application
import com.halil.ozel.covid19stats.utils.NetworkHelper
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class App : Application() {

    @Inject
    lateinit var networkHelper: NetworkHelper

}