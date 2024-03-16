package com.reviro.weather.extensions

import android.content.Context
import android.graphics.Color
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.reviro.weather.R


fun View?.snackbar(message: String, length: Int = Snackbar.LENGTH_SHORT) {
    this?.let {
        val snackbar = Snackbar.make(it, message, length)
            .setBackgroundTint(ContextCompat.getColor(this.context, R.color.red))
            .setTextColor(Color.WHITE)

        val textView = snackbar.view.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
        snackbar.view.background = ContextCompat.getDrawable(
            this.context,
            R.drawable.background_snackbar
        )
        textView.compoundDrawablePadding = 8.dp
        snackbar.show()
    }
}

fun Context.toast(message: String, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, length).show()
}