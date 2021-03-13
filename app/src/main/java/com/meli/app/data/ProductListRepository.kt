package com.meli.app.data

import com.meli.app.utils.MeliResult
import com.meli.app.model.ProductItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProductListRepository(private val productDataSource: ProductDataSource) {

    private val dispatcher = Dispatchers.IO

    suspend fun getProductListByQuery(query: String): MeliResult<List<ProductItem>> {
        return withContext(dispatcher) {
            productDataSource.getProductListByQuery(query)
        }
    }

}