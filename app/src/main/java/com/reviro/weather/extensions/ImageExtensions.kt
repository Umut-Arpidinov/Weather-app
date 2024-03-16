package com.reviro.weather.extensions

import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide

fun ImageView.loadImage(url: String?) {
    Glide.with(this)
        .load(url)
        .into(this)
}