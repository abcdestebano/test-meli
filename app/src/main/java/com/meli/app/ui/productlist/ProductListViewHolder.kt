package com.meli.app.ui.productlist

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.meli.app.R
import com.meli.app.databinding.ItemProductListBinding
import com.meli.app.model.ProductItem
import com.meli.app.utils.extensions.formatToCurrency
import java.text.DecimalFormat
import java.util.*

class ProductListViewHolder(
    private val binding: ItemProductListBinding,
    private val onItemClickProduct: ((ProductItem) -> Unit)
) : RecyclerView.ViewHolder(binding.root) {

    fun bindViewHolder(product: ProductItem) {
        binding.apply {
            root.setOnClickListener {
                onItemClickProduct.invoke(product)
            }
            imgItemProduct.load(product.thumbnail) {
                crossfade(true)
            }
            txvTitleItemProduct.text = product.title
            txvPriceItemProduct.text = product.price.toInt().formatToCurrency()
            txvInstallmentsItemProduct.text =
                " ${product.installments.quantity}x ${removeDecimalsOfInstallment(product.installments.amount)}"
        }
    }

    private fun removeDecimalsOfInstallment(amount: Double): String {
        val decimalFormat = DecimalFormat("#").format(amount)
        return decimalFormat.toInt().formatToCurrency()
    }

}