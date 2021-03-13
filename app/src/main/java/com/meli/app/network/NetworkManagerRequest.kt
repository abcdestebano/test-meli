package com.meli.app.network

import com.meli.app.utils.MeliResult
import retrofit2.Response
import java.net.UnknownHostException

internal inline fun <T : Any> executeRetrofitRequest(request: () -> Response<T>): MeliResult<T> {
    return try {
        val response = request.invoke()
        return if (response.isSuccessful && response.body() != null) {
            val body = response.body()
            if (body != null){
                MeliResult.Success(body)
            } else {
                MeliResult.Error("Empty body found in this request")
            }
        } else {
            val errorBody = response.errorBody()
            val errorText = errorBody?.string() ?: "Error body null"
            MeliResult.Error(errorText)
        }
    } catch (exception: UnknownHostException) {
        MeliResult.Error(exception.toString())
    }
}