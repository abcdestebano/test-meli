package com.meli.app.model

import kotlinx.serialization.Serializable

@Serializable
data class ProductInstallments(
    val quantity: Int,
    val amount: Double
)