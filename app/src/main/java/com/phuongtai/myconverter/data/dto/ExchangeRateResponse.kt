package com.phuongtai.myconverter.data.dto

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@JsonClass(generateAdapter = true)
@Parcelize
data class ExchangeRateResponse (
    @Json(name = "data")
    val data : Map<String, Double> = mapOf(),
) : Parcelable