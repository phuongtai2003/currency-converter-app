package com.phuongtai.myconverter.data


sealed class Resource<T> (
    val data: T? = null,
    val success: Boolean = false,
    val errorCode: Int? = null) {

    class Success<T>(data: T) : Resource<T>(data, true)
    class Loading<T>(data: T? = null) : Resource<T>(data)
    class Error<T>(errorCode: Int) : Resource<T>(null, errorCode = errorCode)

    override fun toString(): String {
        return when(this) {
            is Success -> "Success[data=$data]"
            is Loading -> "Loading"
            is Error -> "Error[errorCode=$errorCode]"
        }
    }
}