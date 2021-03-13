package com.meli.app.model

import com.google.gson.annotations.SerializedName

data class Product(
    val id: String,
    val title: String,
    val price: Long,
    @SerializedName("sold_quantity")
    val soldQuantity: Int,
    val pictures: List<ProductPicture>,
    val attributes: List<ProductAttribute>,
    val warranty: String,
    @SerializedName("initial_quantity")
    val initialQuantity: Int
)