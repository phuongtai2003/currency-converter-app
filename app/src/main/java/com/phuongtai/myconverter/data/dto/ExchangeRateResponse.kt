package com.phuongtai.myconverter.data.dto

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@JsonClass(generateAdapter = true)
@Parcelize
data class ExchangeRateResponse (
    @Json(name = "base")
    val base : String = "",

    @Json(name = "success")
    val success : Boolean = false,

    @Json(name = "timestamp")
    val timestamp : Int = 0,

    @Json(name = "rates")
    val rates : Map<String, Double> = mapOf(),

    @Json(name = "date")
    val date : String = "",

    @Json(name = "error")
    val error : @RawValue Map<String, Any?> = mapOf()
) : Parcelable