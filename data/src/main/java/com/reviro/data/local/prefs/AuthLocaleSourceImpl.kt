package com.reviro.data.local.prefs

import android.content.SharedPreferences
import javax.inject.Inject

class AuthLocaleSourceImpl @Inject constructor(
    private val preferences: SharedPreferences
) : AuthLocaleSource {

    override var hasSavedLatLng: Boolean
        get() = preferences.getBoolean(HAS_SAVED_LOCATION, false)
        set(hasSavedLatLng) {
            preferences.edit().putBoolean(HAS_SAVED_LOCATION, hasSavedLatLng).apply()
        }


    override var latitude: String?
        get() = preferences.getString(LATITUDE, null)
        set(latitude) {
            preferences.edit().putString(LATITUDE, latitude).apply()
        }
    override var longitude: String?
        get() = preferences.getString(LONGITUDE, null)
        set(longitude) {
            preferences.edit().putString(LONGITUDE, longitude).apply()
        }





    companion object {
        private const val HAS_SAVED_LOCATION = "com.reviro.weather.is_first_launch"
        private const val LATITUDE = "com.reviro.weather.latitude"
        private const val LONGITUDE = "com.reviro.weather.longitude"

    }

}