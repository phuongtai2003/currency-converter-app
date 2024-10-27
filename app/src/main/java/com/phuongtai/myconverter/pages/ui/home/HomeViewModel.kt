package com.phuongtai.myconverter.pages.ui.home

import android.util.Log
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.phuongtai.myconverter.R
import com.phuongtai.myconverter.currency_data.CurrencyData
import com.phuongtai.myconverter.data.DataRepositorySource
import com.phuongtai.myconverter.data.Resource
import com.phuongtai.myconverter.data.dto.ExchangeRates
import com.phuongtai.myconverter.data.error.INVALID_INPUT
import com.phuongtai.myconverter.pages.base.BaseViewModel
import com.phuongtai.myconverter.utils.SingleContent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val dataRepository: DataRepositorySource
) : BaseViewModel() {
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    private val exchangeRateLiveDataPrivate = MutableLiveData<Resource<ExchangeRates>>()
    val exchangeRateLiveData: LiveData<Resource<ExchangeRates>> get() = exchangeRateLiveDataPrivate

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    private val countriesLiveDataPrivate = MutableLiveData<List<CurrencyData>>()
    val countriesLiveData: LiveData<List<CurrencyData>> get() = countriesLiveDataPrivate

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    private val fromCurrencyLiveDataPrivate = MutableLiveData<CurrencyData>()
    val fromCurrencyLiveData: LiveData<CurrencyData> get() = fromCurrencyLiveDataPrivate

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    private val toCurrencyLiveDataPrivate = MutableLiveData<CurrencyData>()
    val toCurrencyLiveData: LiveData<CurrencyData> get() = toCurrencyLiveDataPrivate

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    private val showToastLiveDataPrivate = MutableLiveData<SingleContent<Any>>()
    val showToastLiveData: LiveData<SingleContent<Any>> get() = showToastLiveDataPrivate


    fun getCountries() {
        /// Hardcoded list of countries
        val countryCurrencies : List<CurrencyData> = mutableListOf(
            CurrencyData("USD", R.drawable.ic_us),
            CurrencyData("SGD", R.drawable.ic_singapore),
            CurrencyData("CNY", R.drawable.ic_china),
            CurrencyData("AUD", R.drawable.ic_australia),
        )
        countriesLiveDataPrivate.postValue(countryCurrencies)
    }

    fun getExchangeRates(baseCurrencyValue: String) {
        if(baseCurrencyValue.isEmpty()){
            exchangeRateLiveDataPrivate.postValue(Resource.Error(INVALID_INPUT))
        }
        else{
            viewModelScope.launch {
                exchangeRateLiveDataPrivate.postValue(Resource.Loading())
                val baseCurrency = fromCurrencyLiveDataPrivate.value?.currencyValue ?: "USD"
                val toCurrency = toCurrencyLiveDataPrivate.value?.currencyValue ?: "USD"
                dataRepository.getLatestRates(baseCurrency, toCurrency).collect{
                    exchangeRateLiveDataPrivate.postValue(it)
                }
            }
        }
    }

    fun updateFromCurrency(currencyData: CurrencyData) {
        fromCurrencyLiveDataPrivate.postValue(currencyData)
    }

    fun updateToCurrency(currencyData: CurrencyData) {
        toCurrencyLiveDataPrivate.postValue(currencyData)
    }

    fun showToastMessage(errorCode: Int) {
        val error = errorManager.getError(errorCode)
        showToastLiveDataPrivate.value = SingleContent(error.description)
    }
}