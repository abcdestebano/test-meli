package com.meli.app.ui.product

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.meli.app.databinding.ItemProductPictureBinding

class ProductPicturesViewHolder(
    private val binding: ItemProductPictureBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bindViewHolder(image: String) {
        binding.imgItemProductPicture.load(image) {
            crossfade(true)
        }
    }

}