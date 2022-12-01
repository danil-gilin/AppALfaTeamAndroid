package com.example.alfateam.entity.dollar_to_rub


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DollarToRub(
    @Json(name = "date")
    val date: String,
    @Json(name = "info")
    val info: Info,
    @Json(name = "query")
    val query: Query,
    @Json(name = "result")
    val result: Double,
    @Json(name = "success")
    val success: Boolean
)