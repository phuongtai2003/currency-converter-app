package com.phuongtai.myconverter.data.remote.remote

import com.phuongtai.myconverter.data.Resource
import com.phuongtai.myconverter.data.dto.ExchangeRates

internal interface RemoteDataSource {
    suspend fun getLatestExchangeRate(base: String, symbols: String): Resource<ExchangeRates>
}