package com.meli.app.utils.extensions

import java.text.NumberFormat
import java.util.*

internal const val CURRENCY_CODE = "USD"

/**
 * Function extension to format number to currency,
 * this is useful to show the price of our product
 *
 * The currency code was USD because if we put COP,
 * the format of the price appears with COP, with USD just appear the sign $
 *
 * @return
 */
fun Int.formatToCurrency(): String {
    val format: NumberFormat = NumberFormat.getCurrencyInstance()
    format.maximumFractionDigits = 0
    format.currency = Currency.getInstance(CURRENCY_CODE)

    return format.format(this)
}