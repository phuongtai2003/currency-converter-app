package com.phuongtai.myconverter.data

import com.phuongtai.myconverter.data.dto.ExchangeRates
import kotlinx.coroutines.flow.Flow

interface DataRepositorySource {
    suspend fun getLatestRates(base: String, currencies: String): Flow<Resource<ExchangeRates>>
}