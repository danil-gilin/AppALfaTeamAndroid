package com.example.alfateam.entity.dollar


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Dollar(
    @Json(name = "data")
    val `data`: Data,
    @Json(name = "message")
    val message: String,
    @Json(name = "status")
    val status: Int
)