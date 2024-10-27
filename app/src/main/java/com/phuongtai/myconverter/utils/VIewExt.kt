package com.phuongtai.myconverter.utils

import android.app.Service
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.text.PrecomputedTextCompat
import androidx.core.widget.TextViewCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso

/// This file contains some extension functions for View class
/// It helps to make the code more readable and clean

fun View.showKeyboard() {
    (this.context.getSystemService(Service.INPUT_METHOD_SERVICE) as? InputMethodManager)
        ?.showSoftInput(this, 0)
}

fun View.hideKeyboard() {
    (this.context.getSystemService(Service.INPUT_METHOD_SERVICE) as? InputMethodManager)
        ?.hideSoftInputFromWindow(this.windowToken, 0)
}

fun View.toBeVisible() {
    this.visibility = View.VISIBLE
}

fun View.toBeGone() {
    this.visibility = View.GONE
}

fun View.toBeInvisible() {
    this.visibility = View.INVISIBLE
}

fun View.showSnackBar(message: String, duration: Int) {
    Snackbar.make(this, message, duration).show()
}

fun View.setupSnackBar(
    lifecycleOwner: LifecycleOwner,
    snackBarEvent: LiveData<SingleContent<Any>>,
    duration: Int
) {
    snackBarEvent.observe(lifecycleOwner) { event ->
        event.getContentIfNotHandled()?.let {
            when(it) {
                is String -> {
                    hideKeyboard()
                    showSnackBar(it, duration)
                }
                is Int ->{
                    hideKeyboard()
                    showSnackBar(this.context.getString(it), duration)
                }
                else -> {
                    hideKeyboard()
                }
            }
        }
    }
}

fun View.showToast(
    lifecycleOwner: LifecycleOwner,
    toastEvent: LiveData<SingleContent<Any>>,
    duration: Int
) {
    toastEvent.observe(lifecycleOwner) { event ->
        event.getContentIfNotHandled()?.let {
            when(it) {
                is String -> {
                    Toast.makeText(this.context, it, duration).show()
                }
                is Int ->{
                    Toast.makeText(this.context, this.context.getString(it), duration).show()
                }
                else -> {
                }
            }
        }
    }
}

fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher{
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        override fun afterTextChanged(s: Editable?) {
            afterTextChanged.invoke(s.toString())
        }
    })
}

fun ImageView.loadImage(@DrawableRes resId: Int) = Picasso.get().load(resId).into(this)

fun ImageView.loadImage(url: String) = Picasso.get().load(url).into(this)

fun AppCompatTextView.setTextFutureExt(text: String) {
    this.setTextFuture(
        PrecomputedTextCompat.getTextFuture(
        text,
        TextViewCompat.getTextMetricsParams(this),
        null
    ))
}

fun AppCompatTextView.setTextFutureExt(text: CharSequence) {
    this.setTextFuture(
        PrecomputedTextCompat.getTextFuture(
        text,
        TextViewCompat.getTextMetricsParams(this),
        null
    ))
}