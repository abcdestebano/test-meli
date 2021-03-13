package com.meli.app.api

import com.meli.app.model.ProductResultQuery
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductApiService {

    @GET("search")
    suspend fun getProductListByQuery(@Query("q") query: String): Response<ProductResultQuery>

}