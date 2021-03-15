package com.meli.app.network

import android.util.Log
import com.meli.app.utils.MeliResult
import retrofit2.Response
import java.io.IOException
import java.net.UnknownHostException

internal const val TAG = "NetworkManagerRequest"
internal const val ERROR_EMPTY_BODY =  "Empty body found in this request"
internal const val ERROR_NULL_BODY =  "Error body null"

/**
 * This internal inline function just execute rquest of retrofit and handle the response
 * to return the correct MeliResult type
 *
 * @param T
 * @param request
 * @return
 */
internal inline fun <T : Any> executeRetrofitRequest(request: () -> Response<T>): MeliResult<T> {
    return try {
        val response = request.invoke()
        return if (response.isSuccessful && response.body() != null) {
            val body = response.body()
            if (body != null){
                MeliResult.Success(body)
            } else {
                Log.e(TAG, ERROR_EMPTY_BODY)
                MeliResult.Error(ERROR_NULL_BODY)
            }
        } else {
            val errorBody = response.errorBody()
            val errorText = errorBody?.string() ?: ERROR_NULL_BODY
            Log.e(TAG, errorText)
            MeliResult.Error(errorText)
        }
    } catch (exception: UnknownHostException) {
        Log.e(TAG, exception.toString())
        MeliResult.Error(exception.toString())
    }
}