package com.reviro.weather.extensions

import android.content.Context
import android.graphics.Color
import android.opengl.Visibility
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.snackbar.Snackbar
import com.reviro.weather.R


fun View?.snackbar(message: String, length: Int = Snackbar.LENGTH_SHORT) {
    this?.let {
        val snackbar = Snackbar.make(it, message, length)
            .setBackgroundTint(ContextCompat.getColor(this.context, R.color.red))
            .setTextColor(Color.WHITE)

        val textView =
            snackbar.view.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
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


fun ShimmerFrameLayout.setLoading(isLoading: Boolean) {
    visibility = if (isLoading) {
        startShimmer()
        View.VISIBLE
    } else {
        stopShimmer()
        View.GONE
    }
}

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}
