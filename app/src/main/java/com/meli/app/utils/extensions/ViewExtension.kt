package com.meli.app.utils.extensions

import android.view.View

/**
 * Function extension to hide or show a view, this was created
 * to start to have an approach to functional programming
 *
 * @param show
 */
fun View.toggleVisibility(show: Boolean) {
    val visibility = if (show) View.VISIBLE else View.GONE
    this.visibility = visibility
}