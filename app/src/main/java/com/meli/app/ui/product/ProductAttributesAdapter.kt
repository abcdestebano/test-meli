package com.meli.app.ui.product

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.meli.app.databinding.ItemProductAttributeBinding
import com.meli.app.model.ProductAttribute

class ProductAttributesAdapter : RecyclerView.Adapter<ProductAttributesViewHolder>() {

    var productAttributesList: List<ProductAttribute> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductAttributesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = ItemProductAttributeBinding.inflate(layoutInflater, parent, false)
        return ProductAttributesViewHolder(itemView)
    }

    override fun getItemCount(): Int = productAttributesList.size

    override fun onBindViewHolder(holder: ProductAttributesViewHolder, position: Int) {
        val item = productAttributesList[position]
        holder.bindViewHolder(item)
    }
}