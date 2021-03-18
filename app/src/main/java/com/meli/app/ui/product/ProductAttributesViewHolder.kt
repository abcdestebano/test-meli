package com.meli.app.ui.product

import androidx.recyclerview.widget.RecyclerView
import com.meli.app.R
import com.meli.app.databinding.ItemProductAttributeBinding
import com.meli.app.model.ProductAttribute

class ProductAttributesViewHolder(
    private val binding: ItemProductAttributeBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bindViewHolder(productAttribute: ProductAttribute) {
        binding.apply {
            txvNameAttribute.text = "${productAttribute.name}:"
            txvValueAttribute.text =
                productAttribute.valueName ?: root.resources.getString(R.string.text_no_apply)
        }
    }

}