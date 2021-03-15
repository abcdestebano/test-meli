package com.meli.app.utils

/**
 * This sealed class contains every type of a response of remote API to handle
 * when request is loading and returns a Success or an Error type to give the correct Feedback
 * @param T
 */
sealed class MeliResult<out T : Any> {
    data class Success<out T : Any>(val data: T) : MeliResult<T>()
    data class Error(val exception: String?) : MeliResult<Nothing>()
    object Loading : MeliResult<Nothing>()
}
