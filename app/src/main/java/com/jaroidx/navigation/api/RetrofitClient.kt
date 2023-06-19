package com.jaroidx.navigation.api

import com.jaroidx.navigation.utils.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {
    companion object {
        private val instances by lazy {
            if (Constants.DEBUG) {
                val httpLoggingInterceptor = HttpLoggingInterceptor()
                httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                val httpClient =
                    OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build()
                Retrofit.Builder().baseUrl(Constants.DUMMY_URL)
                    .addConverterFactory(GsonConverterFactory.create()).client(httpClient).build()
            } else {
                Retrofit.Builder().baseUrl(Constants.DUMMY_URL)
                    .addConverterFactory(GsonConverterFactory.create()).build()
            }
        }

        val getDummyApi: DummyApi by lazy {
            instances.create(DummyApi::class.java)
        }
    }
}