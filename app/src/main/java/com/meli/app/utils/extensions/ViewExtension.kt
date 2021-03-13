package com.meli.app.utils.extensions

import android.view.View

fun View.toggleVisibility(show: Boolean) {
    val visibility = if (show) View.VISIBLE else View.GONE
    this.visibility = visibility
}