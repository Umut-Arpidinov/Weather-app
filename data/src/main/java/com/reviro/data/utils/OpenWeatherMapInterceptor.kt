package com.reviro.data.utils

import com.reviro.common.utils.AppConstants
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject

class OpenWeatherMapInterceptor @Inject constructor(

) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var originalRequest = chain.request()
        val originalHttpUrl: HttpUrl = originalRequest.url

        // Append the API key as a query parameter
        val urlWithApiKey = originalHttpUrl.newBuilder()
            .addQueryParameter("appid", AppConstants.API_KEY)
            .build()

        // Build the new request with the updated URL
        val requestWithApiKey = originalRequest.newBuilder()
            .url(urlWithApiKey)
            .build()

        // Proceed with the request
        return chain.proceed(requestWithApiKey)
    }
}
