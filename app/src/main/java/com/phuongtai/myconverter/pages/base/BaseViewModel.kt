package com.phuongtai.myconverter.pages.base

import androidx.lifecycle.ViewModel
import com.phuongtai.myconverter.errors.ErrorManager
import javax.inject.Inject

abstract class BaseViewModel : ViewModel() {
    @Inject
    lateinit var errorManager: ErrorManager
}