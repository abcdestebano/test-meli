package com.meli.app.api

import com.meli.app.model.Product
import com.meli.app.model.ProductResultQuery
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductApiService {

    @GET("sites/MCO/search")
    suspend fun getProductListByQuery(@Query("q") query: String): Response<ProductResultQuery>

    @GET("items/{productId}")
    suspend fun getProductById(@Path("productId") productId: String): Response<Product>

}