package com.meli.app.data

import com.meli.app.model.Product
import com.meli.app.utils.MeliResult
import com.meli.app.model.ProductItem

interface ProductDataSource {
    suspend fun getProductListByQuery(query: String): MeliResult<List<ProductItem>>
    suspend fun getProductById(productId: String): MeliResult<Product>
}
