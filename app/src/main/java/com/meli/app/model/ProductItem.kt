package com.meli.app.model

import kotlinx.serialization.Serializable

@Serializable
data class ProductItem(
    val id: String,
    val title: String,
    val price: Long,
    val thumbnail: String,
    val installments: ProductInstallments
)