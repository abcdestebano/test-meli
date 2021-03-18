package com.meli.app.utils.extensions

import com.meli.app.utils.MeliResult

/**
 * Function extension to handle the response of a request
 * to show a success data, a loading state while request
 * is in process or an error state
 *
 * @param T type of data passed
 * @param onSuccess
 * @param onError
 * @param onLoading
 */
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