package com.meli.app.utils

sealed class MeliResult<out T : Any> {
    data class Success<out T : Any>(val data: T) : MeliResult<T>()
    data class Error(val exception: String?) : MeliResult<Nothing>()
    object Loading : MeliResult<Nothing>()
}
