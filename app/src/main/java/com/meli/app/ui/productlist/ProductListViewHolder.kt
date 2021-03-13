package com.meli.app.ui.productlist

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.meli.app.databinding.ItemProductListBinding
import com.meli.app.model.ProductItem
import java.text.DecimalFormat

class ProductListViewHolder(
    private val binding: ItemProductListBinding,
    private val onItemClickProduct: ((ProductItem) -> Unit)
) : RecyclerView.ViewHolder(binding.root) {

    fun bindViewHolder(product: ProductItem) {
        binding.apply {
            root.setOnClickListener {
                onItemClickProduct.invoke(product)
            }
            imgItemProduct.load(product.thumbnail) { crossfade(true) }
            txvTitleItemProduct.text = product.title
            txvPriceItemProduct.text = "$ ${product.price.toString()}"
            txvInstallmentsItemProduct.text =
                " ${product.installments.quantity}x $ ${DecimalFormat("#").format(product.installments.amount)}"
        }
    }

}