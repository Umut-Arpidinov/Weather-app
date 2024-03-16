package com.reviro.weather.extensions

import android.content.res.Resources
import android.util.DisplayMetrics
import android.util.TypedValue
import java.util.Date
import kotlin.time.Duration.Companion.days



fun dpToPx(dp: Float): Int {
    return (dp * Resources.getSystem().displayMetrics.density).toInt()
}

val Int.dp: Int get() = dpToPx(this.toFloat())



