package com.example.alfateam.data.api

import com.example.alfateam.entity.dollar.Dollar
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL="https://currate.ru/"
private const val API_KEY="2a6415e0acaec7dfb272f518d4e318a8"

object RetrofitServiceDollar {
    private val retrofit= Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    val searchDollarApi:DollarApi= retrofit.create(DollarApi::class.java)
}


interface DollarApi {
    @GET("api/?get=rates&pairs=USDRUB&key=$API_KEY")
    suspend fun getDollar(): Dollar
}