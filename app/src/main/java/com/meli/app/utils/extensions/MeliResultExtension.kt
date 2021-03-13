package com.meli.app.utils.extensions

import com.meli.app.utils.MeliResult

fun <T : Any> MeliResult<T>.handleState(
    onSuccess: (T) -> Unit,
    onError: ((MeliResult.Error) -> Unit)? = null,
    onLoading: (() -> Unit)? = null
) {
    when (this) {
        is MeliResult.Success<T> -> onSuccess(this.data)
        is MeliResult.Error -> onError?.invoke(this)
        MeliResult.Loading -> onLoading?.invoke()
    }
}