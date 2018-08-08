package com.freshworks.freshworksandroidgiphy.network

import com.freshworks.freshworksandroidgiphy.utils.API_BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ApiClient {

    object instance {
        var retrofit: Retrofit? = null
        val logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        val httpClient = OkHttpClient.Builder().addInterceptor(logging).build()
    }

    companion object {
        fun getClient(): Retrofit {
            if (instance.retrofit == null) {
                instance.retrofit = Retrofit.Builder()
                        .baseUrl(API_BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(instance.httpClient)
                        .build()
            }
            return instance.retrofit!!
        }
    }


}