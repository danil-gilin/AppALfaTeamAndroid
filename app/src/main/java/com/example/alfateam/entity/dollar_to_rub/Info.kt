package com.example.alfateam.entity.dollar_to_rub


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Info(
    @Json(name = "rate")
    val rate: Double,
    @Json(name = "timestamp")
    val timestamp: Int
)