package com.phuongtai.myconverter.data.error.mapper

import android.content.Context
import com.phuongtai.myconverter.R
import com.phuongtai.myconverter.data.error.*
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ErrorMapper @Inject constructor(@ApplicationContext val context: Context) : ErrorMapperSource {
    override fun getErrorString(errorId: Int): String {
        return context.getString(errorId)
    }

    override val errorsMap: Map<Int, String>
        get() = mapOf<Int, String>(
            Pair(NO_INTERNET_CONNECTION, getErrorString(R.string.no_internet)),
            Pair(NETWORK_ERROR, getErrorString(R.string.network_error)),
            Pair(INVALID_INPUT, getErrorString(R.string.invalid_input))
        ).withDefault {
            getErrorString(R.string.network_error)
        }
}