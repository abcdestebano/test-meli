package com.meli.app.network

import com.meli.app.utils.MeliResult
import com.meli.app.api.ProductApiService
import com.meli.app.data.remote.ProductDataSource
import com.meli.app.model.Product
import com.meli.app.model.ProductResultQuery

/**
 * This class extends of interface ProductDataSource to do
 * every request about Product
 *
 * @property productApiService
 */
class NetworkProductDataSource(
    private val productApiService: ProductApiService
) : ProductDataSource {

    override suspend fun getProductListByQuery(query: String): MeliResult<ProductResultQuery> {
        return executeRetrofitRequest {
            productApiService.getProductListByQuery(query)
        }
    }

    override suspend fun getProductById(productId: String): MeliResult<Product> {
        return executeRetrofitRequest {
            productApiService.getProductById(productId)
        }
    }

}