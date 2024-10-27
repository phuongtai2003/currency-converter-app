package com.phuongtai.myconverter.errors

import com.phuongtai.myconverter.data.error.AppError

interface ErrorUseCase {
    fun getError(errorCode: Int) : AppError
}