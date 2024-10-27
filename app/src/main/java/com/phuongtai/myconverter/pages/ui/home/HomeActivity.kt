package com.phuongtai.myconverter.pages.ui.home

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import androidx.activity.viewModels
import com.phuongtai.myconverter.currency_data.CurrencyData
import com.phuongtai.myconverter.databinding.ActivityHomeBinding
import com.phuongtai.myconverter.pages.base.BaseActivity
import com.phuongtai.myconverter.pages.ui.home.adapter.CurrenciesAdapter
import com.phuongtai.myconverter.utils.observe
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : BaseActivity() {
    private lateinit var binding: ActivityHomeBinding
    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeViewModel.getCountries()
    }

    override fun observeViewModel() {
        observe(homeViewModel.countriesLiveData, ::handleCountriesList)
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


}