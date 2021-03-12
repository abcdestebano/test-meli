package com.meli.app

import androidx.recyclerview.widget.RecyclerView
import com.meli.app.databinding.ItemProductListBinding

class ProductsListViewHolder(
    val binding: ItemProductListBinding,
    val onItemClickProduct: (() -> Unit)
) : RecyclerView.ViewHolder(binding.root) {

    fun bindViewHolder(item: Any) {

    }

}