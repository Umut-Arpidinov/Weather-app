package com.reviro.weather.ui.home

import android.annotation.SuppressLint
import android.location.Location
import android.os.Looper
import android.widget.ArrayAdapter
import androidx.activity.result.contract.ActivityResultContracts
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.reviro.common.base.BaseFragment
import com.reviro.common.utils.AppConstants
import com.reviro.domain.model.WeatherForecast
import com.reviro.weather.R
import com.reviro.weather.databinding.FragmentHomeBinding
import com.reviro.weather.extensions.loadImage
import com.reviro.weather.extensions.setLoading
import com.reviro.weather.extensions.snackbar
import com.reviro.weather.ui.home.HomeViewModel.WeatherThemeState
import com.reviro.weather.ui.home.adapter.HourlyForecastAdapter
import com.reviro.weather.utils.DataFormatter
import com.reviro.weather.utils.PermissionUtils
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber


@AndroidEntryPoint
class HomeFragment :
    BaseFragment<HomeViewModel, FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    override val viewModel: HomeViewModel by hiltNavGraphViewModels(R.id.homeFragment)

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private lateinit var locationsAdapter: ArrayAdapter<String>

    private val locationPermissionRequest =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                getLastLocation()
            }
        }


    private val hourlyForecastAdapter = HourlyForecastAdapter()

    override fun initUi() = with(binding) {
        super.initUi()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        rvHourlyForecast.adapter = hourlyForecastAdapter
        viewModel.getSavedLocations()
        getLastLocation()
    }


    override fun initListeners() = with(binding) {
        super.initListeners()
        ivAdd.setOnClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToSearchLocationFragment()
            )
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

        viewModel.locations.observe(viewLifecycleOwner) {
            onLocationsReceived(it)
        }


    }

    private fun onLocationsReceived(list: List<String>) {
        initLocationsAdapter()
        locationsAdapter.apply {
            clear()
            addAll(list)
        }
        locationsAdapter.apply {
            clear()
            addAll(list)
        }
    }

    private fun initLocationsAdapter() = with(binding) {
        val list = arrayListOf<String>()
        locationsAdapter =
            ArrayAdapter(requireContext(), R.layout.item_spinner, list)
        etLocations.setAdapter(locationsAdapter)
    }


    private fun onWeatherThemeReceived(state: WeatherThemeState) = with(binding) {
        clAppBackground.setBackgroundTheme(state)
    }

    private fun onWeatherReceived(weather: WeatherForecast) = with(binding) {
        tvTemperature.text = getString(R.string.temperature, weather.current.temp.toString())
        tvWeekDay.text = DataFormatter.getWeekday(weather.current.dt.toLong())
        tvDateYear.text = DataFormatter.getMonthAndYear(weather.current.dt.toLong())
        tvLocation.text = weather.timezone.substringAfter('/')
        tvWeatherTitle.text = weather.current.weather.first().description.uppercase()
        val iconUrl = "${AppConstants.ICON_URL + weather.current.weather.first().icon}.png"
        ivWeatherIcon.loadImage(iconUrl)
    }


    override fun onLoading(loading: Boolean) = with(binding) {
        super.onLoading(loading)
        shimmerLayout.setLoading(loading)
        shimmerContent.setLoading(loading)
        rvHourlyForecast.isVisible = !loading
        tvWeatherTitle.isVisible = !loading
        tvTemperature.isVisible = !loading
        ivWeatherIcon.isVisible = !loading
        llCurrentDate.isVisible = !loading
    }

    override fun onError(message: String) = with(binding) {
        super.onError(message)
        view.snackbar(message)
        llCurrentDate.isVisible = false
    }

    override fun onError(throwable: Throwable) = with(binding) {
        super.onError(throwable)
        Timber.d(throwable.message)
        llCurrentDate.isVisible = false
    }


    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        if (PermissionUtils.checkAccessFineLocationGranted(requireContext())) {
            if (PermissionUtils.isLocationEnabled(requireContext())) {
                fusedLocationClient.lastLocation.addOnCompleteListener { task ->
                    val location: Location? = task.result
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
            locationPermissionRequest.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    @SuppressLint("MissingPermission")
    private fun requestNewLocationData() {
        val mLocationRequest = LocationRequest()
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest.interval = 0
        mLocationRequest.fastestInterval = 0
        mLocationRequest.numUpdates = 1

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())

        fusedLocationClient.requestLocationUpdates(
            mLocationRequest, mLocationCallback,
            Looper.myLooper()
        )
    }

    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val mLastLocation: Location? = locationResult.lastLocation
            mLastLocation?.let {
                viewModel.getWeatherByLocation(it.latitude, it.longitude)
            }

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

            WeatherThemeState.UNDEFINED -> {
                setBackgroundResource(R.color.dark_yellow)
            }
        }
    }


}