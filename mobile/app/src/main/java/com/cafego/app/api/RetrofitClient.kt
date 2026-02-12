package com.cafego.app.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    // IP 10.0.2.2 es "localhost" para el Emulador de Android
    private const val BASE_URL = "http://10.0.2.2:8080/"

    val instance: CafeGoApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(CafeGoApiService::class.java)
    }
}