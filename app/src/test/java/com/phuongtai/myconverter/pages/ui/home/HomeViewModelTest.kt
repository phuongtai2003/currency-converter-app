package com.phuongtai.myconverter.pages.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.phuongtai.myconverter.currency_data.CurrencyData
import com.phuongtai.myconverter.data.DataRepositorySource
import com.phuongtai.myconverter.data.Resource
import com.phuongtai.myconverter.data.dto.ExchangeRateResponse
import com.phuongtai.myconverter.data.dto.ExchangeRates
import com.phuongtai.myconverter.data.error.AppError
import com.phuongtai.myconverter.data.error.INVALID_INPUT
import com.phuongtai.myconverter.errors.ErrorManager
import com.phuongtai.myconverter.utils.SingleContent
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import org.junit.Rule
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.argThat
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class HomeViewModelTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: HomeViewModel
    private lateinit var dataRepository: DataRepositorySource
    private var mockErrorManager = mockk<ErrorManager>(relaxed = true)

    /// This is a test dispatcher that runs coroutines immediately (synchronously)
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        dataRepository = mockk()
        viewModel = HomeViewModel(dataRepository).apply {
            this.errorManager = mockErrorManager
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getCountries should return a list of countries`() {
        // Arrange
        val observer = mock<Observer<List<CurrencyData>>>()
        viewModel.countriesLiveData.observeForever(observer)

        // Act
        viewModel.getCountries()

        // Assert
        verify(observer).onChanged(argThat { size == 4 })
    }

    @Test
    fun `getExchangeRates should return error when baseCurrencyValue is empty`() {
        // Arrange
        val observer = mock<Observer<Resource<ExchangeRates>>>()
        viewModel.exchangeRateLiveData.observeForever(observer)

        // Act
        viewModel.getExchangeRates("")

        // Assert
        verify(observer).onChanged(
            argThat{
                this is Resource.Error && errorCode == INVALID_INPUT
            }
        )
    }

    @Test
    fun `updateFromCurrency should update fromCurrencyLiveData`() {
        // Arrange
        val observer = mock<Observer<CurrencyData>>()
        viewModel.fromCurrencyLiveData.observeForever(observer)

        // Act
        viewModel.updateFromCurrency(CurrencyData("USD", 0))

        // Assert
        verify(observer).onChanged(argThat { currencyValue == "USD" })
    }

    @Test
    fun `updateToCurrency should update toCurrencyLiveData`() {
        // Arrange
        val observer = mock<Observer<CurrencyData>>()
        viewModel.toCurrencyLiveData.observeForever(observer)

        // Act
        viewModel.updateToCurrency(CurrencyData("USD", 0))

        // Assert
        verify(observer).onChanged(argThat { currencyValue == "USD" })
    }

    @Test
    fun `test getExchangeRates with valid baseCurrencyValue fetches rates`() = runTest {
        // Arrange
        val baseCurrency = "USD"
        val toCurrency = "SGD"
        val fakeRates = Resource.Success(ExchangeRates(ExchangeRateResponse(
            data = mapOf("SGD" to 1.3)
        )))

        coEvery { dataRepository.getLatestRates(baseCurrency, toCurrency) } returns flowOf(fakeRates)

        val observer = mock<Observer<Resource<ExchangeRates>>>()
        viewModel.exchangeRateLiveData.observeForever(observer)

        viewModel.updateFromCurrency(CurrencyData(baseCurrency, 0))
        viewModel.updateToCurrency(CurrencyData(toCurrency, 0))

        // Act
        viewModel.getExchangeRates("100")

        // Assert
        advanceUntilIdle()
        verify(observer).onChanged(argThat {
            this is Resource.Success && data?.exchangeRateData?.data?.get(toCurrency) == 1.3
        })
    }

}