package com.reviro.weather.ui.home

import android.view.inputmethod.EditorInfo
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.reviro.common.base.BaseFragment
import com.reviro.common.utils.AppConstants
import com.reviro.domain.model.Location
import com.reviro.domain.model.WeatherForecast
import com.reviro.weather.R
import com.reviro.weather.databinding.FragmentSearchLocationBinding
import com.reviro.weather.extensions.hideKeyboard
import com.reviro.weather.extensions.loadImage
import com.reviro.weather.extensions.setLoading
import com.reviro.weather.extensions.snackbar
import com.reviro.weather.ui.home.adapter.HourlyForecastAdapter
import com.reviro.weather.utils.DataFormatter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchLocationFragment : BaseFragment<HomeViewModel, FragmentSearchLocationBinding>(
    FragmentSearchLocationBinding::inflate
) {

    override val viewModel: HomeViewModel by viewModels()

    private val hourlyForecastAdapter = HourlyForecastAdapter()

    override fun initUi() {
        super.initUi()
        initToolBar()
    }

    override fun initListeners() = with(binding) {
        super.initListeners()
        etSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                getWeatherByName()
                view?.hideKeyboard()
            }
            false
        }

        tlSearch.setEndIconOnClickListener {
            getWeatherByName()
        }

        rvHourlyForecast.adapter = hourlyForecastAdapter
    }

    override fun observeViewModel() {
        super.observeViewModel()

        viewModel.location.observe(viewLifecycleOwner) {
            onLocationReceived(it)
        }

        viewModel.weather.observe(viewLifecycleOwner) {
            viewModel.weather.observe(viewLifecycleOwner) {
                onWeatherReceived(it)
                hourlyForecastAdapter.submitList(it.hourly)
            }
        }
    }


    private fun onWeatherReceived(weather: WeatherForecast) = with(binding) {
        tvTemperature.text = getString(R.string.temperature, weather.current.temp.toString())
        tvWeekDay.text = DataFormatter.getWeekday(weather.current.dt.toLong())
        tvDateYear.text = DataFormatter.getMonthAndYear(weather.current.dt.toLong())
        tvWeatherTitle.text = weather.current.weather.first().description
        val iconUrl = "${AppConstants.ICON_URL + weather.current.weather.first().icon}.png"
        ivWeatherIcon.loadImage(iconUrl)
        saveBtn.isVisible = true
    }

    private fun onLocationReceived(location: Location) = with(binding) {
        tvLocation.text = location.name
    }


    private fun getWeatherByName() = with(binding) {
        val query = etSearch.text.toString().trim()
        viewModel.getWeatherByQuery(query)
    }

    override fun onLoading(loading: Boolean) = with(binding) {
        super.onLoading(loading)
        shimmerLayout.setLoading(loading)
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


    private fun initToolBar() = with(binding) {
        toolbar.apply {
            title.text = getString(R.string.search_city)
            ivBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }
}