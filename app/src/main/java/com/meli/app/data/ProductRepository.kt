package com.meli.app.data

import com.meli.app.model.Product
import com.meli.app.utils.MeliResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProductRepository(private val productDataSource: ProductDataSource) {

    private val dispatcher = Dispatchers.IO

    suspend fun getProductById(productId: String): MeliResult<Product> {
        return withContext(dispatcher){
            productDataSource.getProductById(productId)
        }
    }

}