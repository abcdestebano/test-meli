package com.meli.app.data.remote

import com.meli.app.utils.MeliResult
import com.meli.app.model.ProductResultQuery
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProductListRepository(private val productDataSource: ProductDataSource) {

    private val dispatcher = Dispatchers.IO

    suspend fun getProductListByQuery(query: String): MeliResult<ProductResultQuery> {
        return withContext(dispatcher) {
            productDataSource.getProductListByQuery(query)
        }
    }

}