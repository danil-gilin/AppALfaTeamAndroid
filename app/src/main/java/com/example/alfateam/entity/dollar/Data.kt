package com.example.alfateam.entity.dollar


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Data(
    @Json(name = "USDRUB")
    val uSDRUB: String
)