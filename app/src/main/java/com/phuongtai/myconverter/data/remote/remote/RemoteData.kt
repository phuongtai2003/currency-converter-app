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
    override suspend fun getLatestExchangeRate(base: String, symbols: String): Resource<ExchangeRates> {
        val exchangeRateService = serviceGenerator.createService(ExchangeRateService::class.java)
        return when(val response = processCall {
            exchangeRateService.getLatestExchangeRate(
                base,
                symbols
            )
        }) {
            is ExchangeRateResponse -> {
                if(response.success){
                    Resource.Success(ExchangeRates(response))
                }
                else{
                    Resource.Error(response.error["code"] as Int? ?: DEFAULT_ERROR)
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