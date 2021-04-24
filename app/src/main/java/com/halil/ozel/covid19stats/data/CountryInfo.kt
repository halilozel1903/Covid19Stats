package com.halil.ozel.covid19stats.data

class CountryInfo {
    var flag: String? = null
    private var _id: String? = null
    var iso2: String? = null
    var iso3: String? = null
    fun get_id(): String? {
        return _id
    }

    fun set_id(_id: String?) {
        this._id = _id
    }
}