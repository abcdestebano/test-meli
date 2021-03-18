package com.meli.app

import com.meli.app.api.ProductApiService
import com.meli.app.model.Product
import com.meli.app.model.ProductResultQuery
import retrofit2.Response

class FakeProductApiService : ProductApiService {

    var getProductListByQueryResponse: Response<ProductResultQuery>? = null
    var getProductByIdResponse: Response<Product>? = null

    override suspend fun getProductListByQuery(query: String): Response<ProductResultQuery> {
        return getProductListByQueryResponse!!
    }

    override suspend fun getProductById(productId: String): Response<Product> {
        return getProductByIdResponse!!
    }
}