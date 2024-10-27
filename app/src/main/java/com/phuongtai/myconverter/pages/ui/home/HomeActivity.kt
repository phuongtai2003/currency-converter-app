package com.phuongtai.myconverter.pages.ui.home

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import androidx.activity.viewModels
import androidx.lifecycle.LiveData
import com.google.android.material.snackbar.Snackbar
import com.phuongtai.myconverter.currency_data.CurrencyData
import com.phuongtai.myconverter.data.Resource
import com.phuongtai.myconverter.data.dto.ExchangeRates
import com.phuongtai.myconverter.databinding.ActivityHomeBinding
import com.phuongtai.myconverter.pages.base.BaseActivity
import com.phuongtai.myconverter.pages.ui.home.adapter.CurrenciesAdapter
import com.phuongtai.myconverter.utils.SingleContent
import com.phuongtai.myconverter.utils.observe
import com.phuongtai.myconverter.utils.observeEvent
import com.phuongtai.myconverter.utils.showToast
import com.phuongtai.myconverter.utils.toBeGone
import com.phuongtai.myconverter.utils.toBeVisible
import com.phuongtai.myconverter.utils.toEditable
import com.phuongtai.myconverter.utils.toTwoDecimalPlaces
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : BaseActivity() {
    private lateinit var binding: ActivityHomeBinding
    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeViewModel.getCountries()
        val currencyConverterLayout = binding.currencyConverterLayout
        currencyConverterLayout.convertButton.setOnClickListener {
            homeViewModel.getExchangeRates(
                currencyConverterLayout.amountInput.text.toString(),
            )
        }
    }

    override fun observeViewModel() {
        observe(homeViewModel.countriesLiveData, ::handleCountriesList)
        observe(homeViewModel.toCurrencyLiveData, ::handleToCurrency)
        observe(homeViewModel.fromCurrencyLiveData, ::handleFromCurrency)
        observe(homeViewModel.exchangeRateLiveData, ::handleExchangeRate)
        observeToast(homeViewModel.showToastLiveData)
    }

    override fun initViewBinding() {
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    private fun handleCountriesList(countries: List<CurrencyData>) {
        /// Setup amount currency spinner
        setupCurrencySpinner(
            binding.currencyConverterLayout.amountCountrySpinner,
            countries,
            0
        ) { selectedCurrency ->
            homeViewModel.updateFromCurrency(selectedCurrency)
        }

        /// Setup converted currency spinner
        setupCurrencySpinner(
            binding.currencyConverterLayout.convertedAmountCountrySpinner,
            countries,
            1
        ) { selectedCurrency ->
            homeViewModel.updateToCurrency(selectedCurrency)
        }
    }


    private fun setupCurrencySpinner(
        spinner: Spinner,
        countries: List<CurrencyData>,
        defaultSelection: Int,
        onItemSelected: (CurrencyData) -> Unit
    ) {
        val adapter = CurrenciesAdapter(countries, this)
        spinner.adapter = adapter
        spinner.setSelection(defaultSelection)

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                onItemSelected(countries[position])
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }

    private fun handleToCurrency(currency: CurrencyData) {
        val currencyConverterLayout=  binding.currencyConverterLayout
        // Set empty text to avoid showing the previous value
        currencyConverterLayout.amountInput.text = null
        currencyConverterLayout.convertedAmountInput.text = null
    }

    private fun handleFromCurrency(currency: CurrencyData) {
        val currencyConverterLayout=  binding.currencyConverterLayout
        // Set empty text to avoid showing the previous value
        currencyConverterLayout.amountInput.text = null
        currencyConverterLayout.convertedAmountInput.text = null
    }

    private fun handleExchangeRate(resource: Resource<ExchangeRates>) {
        when (resource) {
            is Resource.Success -> {
                hideLoading()
                val exchangeRates = resource.data?.exchangeRateData?.data
                val currencyConverterLayout=  binding.currencyConverterLayout
                val exchangeRateOfSelectedCurrency = exchangeRates?.get(homeViewModel.toCurrencyLiveData.value?.currencyValue) ?: 0.0
                val baseCurrencyValue = currencyConverterLayout.amountInput.text.toString().toDoubleOrNull() ?: 0.0
                val convertedCurrencyValue = baseCurrencyValue * exchangeRateOfSelectedCurrency
                currencyConverterLayout.convertedAmountInput.text = convertedCurrencyValue.toTwoDecimalPlaces()
                    .toEditable()
                showExchangeRate(
                    homeViewModel.fromCurrencyLiveData.value?.currencyValue ?: "",
                    homeViewModel.toCurrencyLiveData.value?.currencyValue ?: "",
                    exchangeRateOfSelectedCurrency
                )
            }
            is Resource.Error -> {
                hideLoading()
                resource.errorCode?.let {
                  homeViewModel.showToastMessage(it)
                }
            }
            is Resource.Loading -> {
                showLoading()
            }
        }
    }

    private fun showLoading() {
        binding.loadingLayout.toBeVisible()
    }

    private fun hideLoading() {
        binding.loadingLayout.toBeGone()
    }

    private fun observeToast(message: LiveData<SingleContent<Any>>) {
        binding.root.showToast(this, message, Snackbar.LENGTH_LONG)
    }

    private fun showExchangeRate(
        fromCurrency: String,
        toCurrency: String,
        exchangeRate: Double,
        ) {
        binding.exchangeRate.text = "1 $fromCurrency = ${exchangeRate.toTwoDecimalPlaces()} $toCurrency"
    }
}