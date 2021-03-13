package com.meli.app.ui.product

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.meli.app.databinding.ItemProductPictureBinding
import com.meli.app.model.ProductPicture

class ProductPicturesAdapter : RecyclerView.Adapter<ProductPicturesViewHolder>() {

    var productPictureList: List<ProductPicture> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductPicturesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = ItemProductPictureBinding.inflate(layoutInflater, parent, false)
        return ProductPicturesViewHolder(itemView)
    }

    override fun getItemCount(): Int = productPictureList.size

    override fun onBindViewHolder(holder: ProductPicturesViewHolder, position: Int) {
        val item = productPictureList[position]
        holder.bindViewHolder(item.url)
    }
}