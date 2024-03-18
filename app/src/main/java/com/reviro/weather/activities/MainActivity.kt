package com.reviro.weather.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.location.LocationServices
import com.reviro.weather.R
import com.reviro.weather.utils.PermissionUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }


    override fun onStart() {
        super.onStart()
        when {
            PermissionUtils.checkAccessFineLocationGranted(this) -> {
                when {
                    PermissionUtils.isLocationEnabled(this) -> {

                    }

                    else -> PermissionUtils.enableLocation(this)
                }
            }

            else -> {
                PermissionUtils.askAccessFineLocationPermission(this, 1)
            }
        }
    }

}