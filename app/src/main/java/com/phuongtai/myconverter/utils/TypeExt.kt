package com.phuongtai.myconverter.utils

import android.text.Editable

fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)
fun Double.toTwoDecimalPlaces() : String = String.format("%.2f", this)
