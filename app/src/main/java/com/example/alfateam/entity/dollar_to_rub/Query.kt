package com.example.alfateam.entity.dollar_to_rub


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Query(
    @Json(name = "amount")
    val amount: Int,
    @Json(name = "from")
    val from: String,
    @Json(name = "to")
    val to: String
)