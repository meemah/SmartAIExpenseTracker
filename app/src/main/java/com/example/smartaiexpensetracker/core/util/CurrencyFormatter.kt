package com.example.smartaiexpensetracker.core.util

import java.text.NumberFormat
import java.util.Locale

private val nairaFormat: NumberFormat =
    NumberFormat.getCurrencyInstance(Locale("en", "NG"))

fun Number?.formatNaira(): String = nairaFormat.format((this?:0))
