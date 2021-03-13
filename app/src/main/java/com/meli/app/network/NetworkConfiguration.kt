package com.meli.app.network

import com.meli.app.BuildConfig
import com.meli.app.api.ProductApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun buildRetrofit(): Retrofit = Retrofit.Builder()
    .baseUrl(BuildConfig.BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

fun createProductApiService(retrofit: Retrofit): ProductApiService =
    retrofit.create(ProductApiService::class.java)


