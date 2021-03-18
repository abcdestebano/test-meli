package com.meli.app.ui.productlist

import android.app.Activity
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.meli.app.R
import com.meli.app.databinding.ActivityProductListBinding
import com.meli.app.di.PRODUCT_LIST_VIEW_MODEL
import com.meli.app.model.ProductItem
import com.meli.app.ui.product.ProductActivity
import com.meli.app.ui.search.SearchActivity
import com.meli.app.utils.NetworkReceiver
import com.meli.app.utils.extensions.handleState
import com.meli.app.utils.extensions.isOnline
import com.meli.app.utils.extensions.toggleVisibility
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named

class ProductListActivity : AppCompatActivity() {

    private val productListViewModel: ProductListViewModel by viewModel(
        named(PRODUCT_LIST_VIEW_MODEL)
    )

    private val networkReceiver = object : NetworkReceiver() {
        override fun broadcastResult(connected: Boolean) {
            if (!connected && productListAdapter.productList.isEmpty()) setEmptyStateOffline()
        }
    }

    private val onItemClickProduct: ((ProductItem) -> Unit) = { product ->
        goToProduct(product)
    }

    private val productListAdapter = ProductListAdapter(onItemClickProduct)

    private var binding: ActivityProductListBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductListBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        if (productListViewModel.productList.value?.isNotEmpty() == true) {
            setDataProductList(productListViewModel.productList.value)
        } else {
            setInitialEmptyState()
        }

        setUpRecycler()
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

    private fun handleClickListeners() {
        binding?.apply {
            imgMenu.setOnClickListener { showComingSoon() }
            imgShoppingCart.setOnClickListener { showComingSoon() }
            edtSearch.setOnClickListener { goToSearch() }
        }
    }

    private fun goToSearch() {
        val intent = Intent(this, SearchActivity::class.java)
        startActivityForResult(intent, 0)
    }

    private fun getProductListByQuery() {
        val query = binding?.edtSearch?.text.toString()
        if (query.isNotEmpty()) {
            productListViewModel.getProductListByQuery(query)
            productListViewModel.resultProductList.observe(this, { result ->
                result.handleState(
                    onLoading = {
                        binding?.rvProductList?.toggleVisibility(show = false)
                        toggleEmptyState(false)
                        toggleStateLoading(true)
                    },
                    onSuccess = { data ->
                        productListViewModel.setProductList(data.results)
                        setDataProductList(data.results)
                    },
                    onError = { setStateError() }
                )
            })
        }
    }

    private fun toggleStateLoading(showLoading: Boolean) {
        binding?.loading?.toggleVisibility(show = showLoading)
    }

    private fun setDataProductList(productList: List<ProductItem>?) {
        if (productList?.isNotEmpty() == true) {
            binding?.rvProductList?.toggleVisibility(show = true)
            productListAdapter.productList = productList
            productListAdapter.notifyDataSetChanged()
        } else {
            setEmptyStateProductList()
        }
        toggleStateLoading(false)
    }

    private fun toggleEmptyState(showEmptyState: Boolean) {
        binding?.emptyState?.toggleVisibility(show = showEmptyState)
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
        val intent = Intent(this, ProductActivity::class.java).apply {
            putExtra(ProductActivity.PRODUCT_ID_KEY, product.id)
        }
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

    private fun setEmptyStateOffline() {
        binding?.emptyState?.apply {
            setContentView(
                image = R.drawable.ic_satellite,
                title = resources.getString(R.string.text_offline_empty_state),
                description = resources.getString(R.string.description_offline_empty_state),
                onButtonClick = { if (context.isOnline()) setInitialEmptyState() }
            )
        }
    }

    private fun showComingSoon() {
        Toast.makeText(
            applicationContext,
            resources.getString(R.string.text_coming_soon),
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            data?.let {
                val query = it.getStringExtra(SearchActivity.QUERY)
                binding?.edtSearch?.setText(query)
                getProductListByQuery()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(networkReceiver)
        binding?.rvProductList?.adapter = null
        binding = null
    }
}