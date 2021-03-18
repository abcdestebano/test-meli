package com.meli.app.ui.productlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.meli.app.databinding.ItemProductListBinding
import com.meli.app.model.ProductItem

class ProductListAdapter(
    private val onItemClickProduct: ((ProductItem) -> Unit)
) : RecyclerView.Adapter<ProductListViewHolder>() {

    var productList: List<ProductItem> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = ItemProductListBinding.inflate(layoutInflater, parent, false)
        return ProductListViewHolder(itemView, onItemClickProduct)
    }

    override fun getItemCount(): Int = productList.size

    override fun onBindViewHolder(holder: ProductListViewHolder, position: Int) {
        val item = productList[position]
        holder.bindViewHolder(item)
    }

}