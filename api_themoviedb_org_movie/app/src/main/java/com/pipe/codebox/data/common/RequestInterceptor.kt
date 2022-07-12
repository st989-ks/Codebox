package com.pipe.codebox.data.common

import com.pipe.codebox.BuildConfig
import com.pipe.codebox.extensions.SharedPrefs
import okhttp3.Interceptor
import okhttp3.Response

class RequestInterceptor: Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val url = chain.request()
            .url
            .newBuilder()
            .addQueryParameter("api_key", BuildConfig.API_KEY)
            .build()
        val newRequest = chain.request()
            .newBuilder()
            .url(url)
            .build()
        return chain.proceed(newRequest)
    }
}