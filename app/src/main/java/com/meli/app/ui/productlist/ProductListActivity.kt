package com.meli.app.ui.productlist

import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.meli.app.R
import com.meli.app.databinding.ActivityMainBinding
import com.meli.app.di.PRODUCT_VIEW_MODEL
import com.meli.app.model.ProductItem
import com.meli.app.ui.product.ProductActivity
import com.meli.app.utils.NetworkReceiver
import com.meli.app.utils.extensions.handleState
import com.meli.app.utils.extensions.isOnline
import com.meli.app.utils.extensions.toggleVisibility
import org.koin.android.ext.android.bind
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named

class ProductListActivity : AppCompatActivity() {

    private val productListViewModel: ProductListViewModel by viewModel(named(PRODUCT_VIEW_MODEL))

    private var networkReceiver = object : NetworkReceiver() {
        override fun broadcastResult(connected: Boolean) {
            if (!connected) setEmptyStateOffline()
        }
    }

    private val onItemClickProduct: ((ProductItem) -> Unit) = { product ->
        goToProduct(product)
    }

    private val productListAdapter = ProductListAdapter(onItemClickProduct)

    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setUpRecycler()
        setInitialEmptyState()
        handleClickListeners()
        registerReceiverNetworkState()
    }

    private fun setUpRecycler() {
        binding?.rvProductList?.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = productListAdapter
        }
    }

    private fun setInitialEmptyState() {
        binding?.emptyState?.apply {
            setContentView(
                image = R.drawable.ic_search_meli,
                title = resources.getString(R.string.text_search_empty_state),
            )
        }
    }

    private fun setEmptyStateOffline() {
        binding?.emptyState?.apply {
            setContentView(
                image = R.drawable.ic_satellite,
                title = resources.getString(R.string.text_offline_empty_state),
                onButtonClick = { if (context.isOnline()) setInitialEmptyState() }
            )
        }
    }

    private fun handleClickListeners() {
        binding?.apply {
            imgMenu.setOnClickListener { showComingSoon() }
            imgShoppingCart.setOnClickListener { showComingSoon() }
            edtSearchViewItems.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    getProductListByQuery()
                }
                false
            }
        }
    }

    private fun getProductListByQuery() {
        val query = binding?.edtSearchViewItems?.text.toString()
        if (query.isNotEmpty()) {
            productListViewModel.getListProductsByQuery(query)
            productListViewModel.productList.observe(this, { result ->
                result.handleState(
                    onLoading = {
                        binding?.rvProductList?.toggleVisibility(show = false)
                        toggleEmptyState(false)
                        toggleStateLoading(true)
                    },
                    onSuccess = { data -> setDataProductList(data) },
                    onError = { setStateError() }
                )
            })
        }
    }

    private fun toggleEmptyState(showEmptyState: Boolean) {
        binding?.emptyState?.toggleVisibility(show = showEmptyState)
    }

    private fun toggleStateLoading(showLoading: Boolean) {
        binding?.loading?.toggleVisibility(show = showLoading)
    }

    private fun setDataProductList(productList: List<ProductItem>) {
        if (productList.isNotEmpty()) {
            binding?.rvProductList?.toggleVisibility(show = true)
            productListAdapter.productList = productList
            productListAdapter.notifyDataSetChanged()
        } else {
            setEmptyStateProductList()
        }
        toggleStateLoading(false)
    }

    private fun setEmptyStateProductList() {
        toggleEmptyState(true)
        binding?.emptyState?.apply {
            setContentView(
                image = R.drawable.ic_search_meli,
                title = resources.getString(R.string.text_products_empty_state),
                description = resources.getString(R.string.description_products_empty_state)
            )
        }
    }

    private fun goToProduct(product: ProductItem) {
        val intent = Intent(this, ProductActivity::class.java)
        startActivity(intent)
    }

    private fun setStateError() {
        toggleEmptyState(true)
        toggleStateLoading(false)
    }

    private fun registerReceiverNetworkState() {
        registerReceiver(
            networkReceiver,
            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        )
    }

    private fun showComingSoon() {
        Toast.makeText(
            applicationContext,
            resources.getString(R.string.text_coming_soon),
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(networkReceiver)
        binding = null
    }
}