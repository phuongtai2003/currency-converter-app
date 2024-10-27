package com.phuongtai.myconverter.data.remote.remote.service

import com.phuongtai.myconverter.data.dto.ExchangeRateResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ExchangeRateService {
    @GET("latest")
    suspend fun getLatestExchangeRate(
        @Query("base") base: String,
        @Query("symbols") symbols: String
    ): Response<ExchangeRateResponse>
}