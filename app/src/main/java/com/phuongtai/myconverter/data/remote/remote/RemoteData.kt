package com.phuongtai.myconverter.data.remote.remote

import com.phuongtai.myconverter.data.Resource
import com.phuongtai.myconverter.data.dto.ExchangeRateResponse
import com.phuongtai.myconverter.data.dto.ExchangeRates
import com.phuongtai.myconverter.data.error.*
import com.phuongtai.myconverter.data.remote.remote.service.ExchangeRateService
import com.phuongtai.myconverter.utils.INetworkConnectivity
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class RemoteData @Inject constructor(private val serviceGenerator: ServiceGenerator, private val networkConnectivity: INetworkConnectivity) : RemoteDataSource {
    override suspend fun getLatestExchangeRate(base: String, currencies: String): Resource<ExchangeRates> {
        val exchangeRateService = serviceGenerator.createService(ExchangeRateService::class.java)
        return when(val response = processCall {
            exchangeRateService.getLatestExchangeRate(
                base,
                currencies
            )
        }) {
            is ExchangeRateResponse -> {
                Resource.Success(ExchangeRates(response))
            }
            is Int -> {
                when(response) {
                    401 -> {
                        Resource.Error(UNAUTHORIZED)
                    }
                    403 -> {
                        Resource.Error(NOT_ALLOWED)
                    }
                    404 -> {
                        Resource.Error(NOT_FOUND)
                    }
                    422 -> {
                        Resource.Error(INVALID_INPUT)
                    }
                    else -> {
                        Resource.Error(DEFAULT_ERROR)
                    }
                }
            }
            else ->{
                Resource.Error(DEFAULT_ERROR)
            }
        }
    }

    private suspend fun processCall(responseCall: suspend () -> Response<*>): Any? {
        if (!networkConnectivity.isConnected()) {
            return NO_INTERNET_CONNECTION
        }
        return try {
            val response = responseCall.invoke()
            val responseCode = response.code()
            if (response.isSuccessful) {
                response.body()
            } else {
                responseCode
            }
        } catch (e: IOException) {
            NETWORK_ERROR
        }
    }

}