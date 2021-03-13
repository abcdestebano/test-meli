package com.meli.app.network

import com.meli.app.utils.MeliResult
import com.meli.app.api.ProductApiService
import com.meli.app.data.ProductDataSource
import com.meli.app.model.ProductItem

class NetworkProductDataSource(
    private val productApiService: ProductApiService
) : ProductDataSource {

    override suspend fun getProductListByQuery(query: String): MeliResult<List<ProductItem>> {
        val result = executeRetrofitRequest {
            productApiService.getProductListByQuery(query)
        }
        return if (result is MeliResult.Success) {
            MeliResult.Success(result.data.results)
        } else {
            MeliResult.Error(result.toString())
        }
    }

}