package com.reviro.weather.ui.home

import android.annotation.SuppressLint
import android.location.Location
import android.os.Looper
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.reviro.common.base.BaseFragment
import com.reviro.domain.model.WeatherForecast
import com.reviro.weather.R
import com.reviro.weather.databinding.FragmentHomeBinding
import com.reviro.weather.extensions.loadImage
import com.reviro.weather.extensions.snackbar
import com.reviro.weather.ui.home.adapter.HourlyForecastAdapter
import com.reviro.weather.utils.DataFormatter
import com.reviro.weather.utils.PermissionUtils
import dagger.hilt.android.AndroidEntryPoint
import com.reviro.weather.ui.home.HomeViewModel.WeatherThemeState
import timber.log.Timber


@AndroidEntryPoint
class HomeFragment :
    BaseFragment<HomeViewModel, FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    override val viewModel: HomeViewModel by hiltNavGraphViewModels(R.id.nav_main)

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private val locationRequest = LocationRequest.Builder(
        LocationRequest.PRIORITY_HIGH_ACCURACY, 2000
    )


    private val hourlyForecastAdapter = HourlyForecastAdapter()

    override fun initUi() = with(binding) {
        super.initUi()

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        viewModel.getWeatherByLocation(42.8746, 74.5698)
        rvHourlyForecast.adapter = hourlyForecastAdapter
    }


    override fun initListeners() = with(binding) {
        super.initListeners()
        btnFiveDayForecast.setOnClickListener {
            getLastLocation()
        }
    }

    override fun observeViewModel() {
        super.observeViewModel()
        viewModel.weather.observe(viewLifecycleOwner) {
            onWeatherReceived(it)
            hourlyForecastAdapter.submitList(it.hourly)
        }

        viewModel.weatherThemeState.observe(viewLifecycleOwner) {
            onWeatherThemeReceived(it)
        }
    }

    private fun onWeatherThemeReceived(state: WeatherThemeState) = with(binding) {
        clAppBackground.setBackgroundTheme(state)
    }

    private fun onWeatherReceived(weather: WeatherForecast) = with(binding) {
        tvTemperature.text = getString(R.string.temperature, weather.current.temp.toString())
        tvWeekDay.text = DataFormatter.getWeekday(weather.current.dt.toLong())
        tvDateYear.text = DataFormatter.getMonthAndYear(weather.current.dt.toLong())
        tvWeatherTitle.text = weather.current.weather.first().main
        val iconUrl = "https://openweathermap.org/img/w/${weather.current.weather.first().icon}.png"
        ivWeatherIcon.loadImage(iconUrl)

    }


    override fun onLoading(loading: Boolean) = with(binding) {
        super.onLoading(loading)
        progressBar.isVisible = loading
    }

    override fun onError(message: String) {
        super.onError(message)
        view.snackbar(message)
    }

    override fun onError(throwable: Throwable) {
        super.onError(throwable)
        Timber.d(throwable.message)
    }


    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        if (PermissionUtils.checkAccessFineLocationGranted(requireContext())) {
            if (PermissionUtils.isLocationEnabled(requireContext())) {

                fusedLocationClient.lastLocation.addOnCompleteListener { task ->
                    var location: Location? = task.result
                    if (location == null) {
                        requestNewLocationData()
                    } else {
                        val lat = location.latitude
                        val long = location.longitude

                        viewModel.getWeatherByLocation(lat, long)
                    }
                }
            } else {
                PermissionUtils.enableLocation(requireActivity())
            }
        } else {
            PermissionUtils.askAccessFineLocationPermission(requireActivity(), 1)
        }
    }

    @SuppressLint("MissingPermission")
    private fun requestNewLocationData() {
        var mLocationRequest = LocationRequest()
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest.interval = 0
        mLocationRequest.fastestInterval = 0
        mLocationRequest.numUpdates = 1

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())

        fusedLocationClient!!.requestLocationUpdates(
            mLocationRequest, mLocationCallback,
            Looper.myLooper()
        )
    }

    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            var mLastLocation: Location? = locationResult.lastLocation
        }
    }


    private fun ConstraintLayout.setBackgroundTheme(state: WeatherThemeState) {
        when (state) {
            WeatherThemeState.RAIN -> {
                setBackgroundResource(R.drawable.state_rainy)
            }

            WeatherThemeState.CLOUD -> {
                setBackgroundResource(R.drawable.state_clouds)
            }

            WeatherThemeState.CLEAR -> {
                setBackgroundResource(R.drawable.state_clear)
            }

            WeatherThemeState.SNOW -> {
                setBackgroundResource(R.drawable.state_snow)
            }
        }
    }


}