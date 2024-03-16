package com.reviro.weather.ui.home

import androidx.core.view.isVisible
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import com.reviro.common.base.BaseFragment
import com.reviro.domain.model.WeatherForecast
import com.reviro.weather.R
import com.reviro.weather.databinding.FragmentHomeBinding
import com.reviro.weather.extensions.loadImage
import com.reviro.weather.extensions.snackbar
import com.reviro.weather.ui.home.adapter.HourlyForecastAdapter
import com.reviro.weather.utils.DataFormatter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment :
    BaseFragment<HomeViewModel, FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    override val viewModel: HomeViewModel by hiltNavGraphViewModels(R.id.nav_main)


    private val hourlyForecastAdapter = HourlyForecastAdapter()

    override fun initUi() = with(binding) {
        super.initUi()
        viewModel.getWeatherByLocation(42.8746, 74.5698)
        rvHourlyForecast.adapter = hourlyForecastAdapter
    }


    override fun initListeners() {
        super.initListeners()
    }

    override fun observeViewModel() {
        super.observeViewModel()
        viewModel.weather.observe(viewLifecycleOwner) {
            onWeatherReceived(it)
            hourlyForecastAdapter.submitList(it.hourly)
        }
    }

    private fun onWeatherReceived(weather: WeatherForecast) = with(binding) {
        tvTemperature.text = getString(R.string.temperature, weather.current.temp.toString())
        tvWeekDay.text = DataFormatter.getWeekday(weather.current.dt.toLong())
        tvDateYear.text = DataFormatter.getMonthAndYear(weather.current.dt.toLong())
        tvWeatherTitle.text = weather.current.weather.first().main
        val iconUrl = "https://openweathermap.org/img/w/${weather.current.weather}.png"
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

}