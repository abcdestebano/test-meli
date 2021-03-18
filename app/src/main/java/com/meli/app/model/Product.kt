package com.meli.app.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Product(
    val id: String,
    val title: String,
    val price: Long,
    @SerialName("sold_quantity")
    val soldQuantity: Int,
    val pictures: List<ProductPicture>,
    val attributes: List<ProductAttribute>,
    val warranty: String,
    @SerialName("available_quantity")
    val availableQuantity: Int
)