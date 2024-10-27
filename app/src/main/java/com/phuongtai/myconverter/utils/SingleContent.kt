package com.phuongtai.myconverter.utils


// This class is used as a wrapper for data that is exposed via a LiveData that represents an event.
open class SingleContent<out T>(private val content: T) {
    // This is a class that wraps an object in a single content
    var hasBeenHandled = false
        private set // Allow external read but not write

    // Returns the content and prevents its use again.
    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    // Returns the content, even if it's already been handled.
    fun peekContent(): T = content
}