package com.meli.app.model

import kotlinx.serialization.Serializable

@Serializable
data class ProductResultQuery(
    val results: List<ProductItem>
)
