package com.phuongtai.myconverter.pages.ui.home

import android.util.Log
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.phuongtai.myconverter.R
import com.phuongtai.myconverter.currency_data.CurrencyData
import com.phuongtai.myconverter.data.DataRepositorySource
import com.phuongtai.myconverter.data.Resource
import com.phuongtai.myconverter.data.dto.ExchangeRates
import com.phuongtai.myconverter.pages.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val dataRepository: DataRepositorySource
) : BaseViewModel() {
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val exchangeRateLiveDataPrivate = MutableLiveData<Resource<ExchangeRates>>()
    val exchangeRateLiveData: LiveData<Resource<ExchangeRates>> get() = exchangeRateLiveDataPrivate

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val countriesLiveDataPrivate = MutableLiveData<List<CurrencyData>>()
    val countriesLiveData: LiveData<List<CurrencyData>> get() = countriesLiveDataPrivate

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val fromCurrencyLiveDataPrivate = MutableLiveData<CurrencyData>()
    val fromCurrencyLiveData: LiveData<CurrencyData> get() = fromCurrencyLiveDataPrivate

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val toCurrencyLiveDataPrivate = MutableLiveData<CurrencyData>()
    val toCurrencyLiveData: LiveData<CurrencyData> get() = toCurrencyLiveDataPrivate

    fun getCountries() {
        val countryCurrencies : List<CurrencyData> = mutableListOf(
            CurrencyData("USD", R.drawable.ic_us),
            CurrencyData("VND", R.drawable.ic_vietnam),
            CurrencyData("CNY", R.drawable.ic_china),
            CurrencyData("AUD", R.drawable.ic_australia),
        )
        countriesLiveDataPrivate.postValue(countryCurrencies)
    }

    fun updateFromCurrency(currencyData: CurrencyData) {
        fromCurrencyLiveDataPrivate.postValue(currencyData)
    }

    fun updateToCurrency(currencyData: CurrencyData) {
        toCurrencyLiveDataPrivate.postValue(currencyData)
    }
}