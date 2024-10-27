package com.phuongtai.myconverter.data.error

class AppError(val code: Int, val description: String) {
    constructor(exception: Exception) : this(code = DEFAULT_ERROR, description = exception.message ?: "")
}

const val DEFAULT_ERROR = 0
const val UNAUTHORIZED = 401
const val NOT_ALLOWED = 403
const val NOT_FOUND = 404
const val NO_INTERNET_CONNECTION = -1
const val NETWORK_ERROR = -2
const val INVALID_INPUT = -3