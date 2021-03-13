package com.meli.app.data

import com.meli.app.utils.MeliResult
import com.meli.app.model.ProductItem

interface ProductDataSource {
    suspend fun getProductListByQuery(query: String): MeliResult<List<ProductItem>>
}