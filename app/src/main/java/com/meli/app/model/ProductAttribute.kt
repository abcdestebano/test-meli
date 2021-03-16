package com.meli.app.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductAttribute(
    val id: String,
    val name: String,
    @SerialName("value_name")
    var valueName: String?
)