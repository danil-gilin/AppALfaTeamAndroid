package com.example.alfateam.data.api

import com.example.alfateam.entity.Constance
import com.example.alfateam.entity.dollar_to_rub.DollarToRub
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers

private const val BASE_URL="https://api.apilayer.com/"


object RetrofitServiceDollar {
    private val retrofit= Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    val searchDollarApi:DollarApi= retrofit.create(DollarApi::class.java)
}


interface DollarApi {
    @Headers("apikey:7qPdgJYo7qNTDSVNeBPEWEPd6uI0g8Hj")
    @GET("fixer/convert?to=RUB&from=USD&amount=1")
    suspend fun getDollar(): DollarToRub
}