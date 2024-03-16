package com.reviro.weather.ui.home.adapter

import android.provider.ContactsContract.Data
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.reviro.domain.model.Hourly
import com.reviro.weather.R
import com.reviro.weather.databinding.ItemHourlyBinding
import com.reviro.weather.extensions.loadImage
import com.reviro.weather.utils.DataFormatter
import timber.log.Timber

class HourlyForecastAdapter :
    ListAdapter<Hourly, HourlyForecastAdapter.ItemViewHolder>(diffCallback) {


    inner class ItemViewHolder(private val binding: ItemHourlyBinding) : ViewHolder(binding.root) {
        fun bind(hourly: Hourly, position: Int) = with(binding) {
            tvTemperature.text =
                itemView.context.getString(R.string.hourly_temp, hourly.temp.toString())
            val windSpeed = DataFormatter.convertToKmPerHour(hourly.wind_speed)
            tvWindSpeed.text = itemView.context.getString(R.string.km_per_hour, windSpeed.toString())
            val iconUrl = "https://openweathermap.org/img/w/${hourly.weather.first().icon}.png"
            tvTime.text = DataFormatter.getHours(hourly.dt.toLong())
            ivWeatherIcon.loadImage(iconUrl)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = ItemHourlyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<Hourly>() {
            override fun areItemsTheSame(oldItem: Hourly, newItem: Hourly): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Hourly, newItem: Hourly): Boolean {
                return oldItem.weather == newItem.weather
            }

        }
    }
}