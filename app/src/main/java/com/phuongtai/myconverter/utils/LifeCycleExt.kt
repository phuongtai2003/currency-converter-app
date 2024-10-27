package com.phuongtai.myconverter.utils

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData

fun <T> LifecycleOwner.observe(liveData: LiveData<T>, action: (t : T) -> Unit) {
    liveData.observe(this) {
        it?.let {
            action(it)
        }
    }
}

fun <T> LifecycleOwner.observeEvent(liveData: LiveData<SingleContent<T>>, action: (t: SingleContent<T>) -> Unit) {
    liveData.observe(this) {
        it?.let {
            action(it)
        }
    }
}