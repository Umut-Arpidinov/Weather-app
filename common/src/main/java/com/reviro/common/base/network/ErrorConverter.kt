package com.reviro.common.base.network

import android.content.res.Resources
import androidx.annotation.StringRes
import com.reviro.common.R
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import javax.inject.Inject


data class MessageException(@StringRes val stringResId: Int): Exception()

data class StringMessageException(val msg: String): Exception()


class ErrorConverter @Inject constructor(
    private val resources: Resources
) {
    fun convert(error: Throwable?): String =
        when(error) {
            is HttpException -> getMessageFrom(error)
            is MessageException -> resources.getString(error.stringResId)
            is StringMessageException -> error.msg
            is SocketTimeoutException -> resources.getString(R.string.error_timeout)
            is IOException -> resources.getString(R.string.error_network_error)
            else -> {
                resources.getString(R.string.error_unknown_error)

            }
        }

    private fun getMessageFrom(exception: HttpException): String {
        return try {
            val str = exception.response()?.errorBody()?.string()!!
            val jsonObject = JSONObject(str)
            jsonObject.getString("message")
        } catch (e: Exception) {
            resources.getString(R.string.error_unknown_error)
        }

    }


}