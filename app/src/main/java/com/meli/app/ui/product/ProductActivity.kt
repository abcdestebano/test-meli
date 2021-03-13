package com.meli.app.ui.product

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.meli.app.databinding.ActivityProductBinding
import com.meli.app.di.PRODUCT_VIEW_MODEL
import com.meli.app.model.Product
import com.meli.app.model.ProductAttribute
import com.meli.app.model.ProductPicture
import com.meli.app.utils.extensions.handleState
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named

class ProductActivity : AppCompatActivity() {

    private val productViewModel: ProductViewModel by viewModel(named(PRODUCT_VIEW_MODEL))

    private var binding: ActivityProductBinding? = null

    private val productPicturesAdapter = ProductPicturesAdapter()
    private val productAttributesAdapter = ProductAttributesAdapter()

    companion object {
        const val PRODUCT_ID_KEY = "productId"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setUpToolbar()
        setUpRecyclerAttributes()

        val productId = intent.getStringExtra(PRODUCT_ID_KEY)
        productId?.let { getProductById(it) }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun setUpToolbar() {
        setSupportActionBar(binding?.toolbarProduct)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setUpRecyclerAttributes() {
        binding?.rvAttributes?.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = productAttributesAdapter
        }
    }

    private fun getProductById(productId: String) {
        productViewModel.getProductById(productId)
        productViewModel.product.observe(this, { result ->
            result.handleState(
                onLoading = {},
                onSuccess = { data -> setDataProduct(data) },
                onError = {}
            )
        })
    }

    private fun setDataProduct(product: Product) {
        binding?.apply {
            txvSoldQuantity.text = "${product.soldQuantity} vendidos"
            txvTitle.text = product.title
            txvPrice.text = "$ ${product.price}"
            txvStockAvailable.text = "Stock disponible ${product.availableQuantity}"
        }
        setProductPictures(product.pictures)
        setProductAttributes(product.attributes)
    }

    private fun setProductPictures(pictures: List<ProductPicture>) {
        binding?.viewPagerPictures?.adapter = productPicturesAdapter
        productPicturesAdapter.productPictureList = pictures
        productPicturesAdapter.notifyDataSetChanged()
        binding?.viewPagerPictures?.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding?.chipCountPictures?.text = "${position.plus(1)} / ${pictures.size}"
            }
        })
    }

    private fun setProductAttributes(attributes: List<ProductAttribute>) {
        productAttributesAdapter.productAttributesList = attributes.take(10)
        productAttributesAdapter.notifyDataSetChanged()
    }

}