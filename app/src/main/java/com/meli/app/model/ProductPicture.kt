package com.meli.app.model

import kotlinx.serialization.Serializable

@Serializable
data class ProductPicture(
    val id: String,
    val url: String
)