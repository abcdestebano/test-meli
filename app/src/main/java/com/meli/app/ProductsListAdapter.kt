package com.meli.app

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.meli.app.databinding.ItemProductListBinding

class ProductsListAdapter(
    private val onItemClickProduct: (() -> Unit)
) : RecyclerView.Adapter<ProductsListViewHolder>() {

    val listProducts = listOf<Any>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = ItemProductListBinding.inflate(layoutInflater, parent, false)
        return ProductsListViewHolder(itemView, onItemClickProduct)
    }

    override fun getItemCount(): Int = listProducts.size

    override fun onBindViewHolder(holder: ProductsListViewHolder, position: Int) {
        val item = listProducts[position]
        holder.bindViewHolder(item)
    }

}