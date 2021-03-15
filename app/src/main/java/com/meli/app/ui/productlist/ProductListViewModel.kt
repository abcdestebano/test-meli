package com.meli.app.ui.productlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meli.app.data.ProductListRepository
import com.meli.app.model.ProductItem
import com.meli.app.model.ProductResultQuery
import com.meli.app.utils.MeliResult
import kotlinx.coroutines.launch

class ProductListViewModel(private val productListRepository: ProductListRepository) : ViewModel() {

    private val _resultProductList: MutableLiveData<MeliResult<ProductResultQuery>> =
        MutableLiveData()
    val resultProductList: LiveData<MeliResult<ProductResultQuery>> = _resultProductList

    private val _productList: MutableLiveData<List<ProductItem>> = MutableLiveData()
    val productList: LiveData<List<ProductItem>> = _productList

    fun getProductListByQuery(query: String) {
        _resultProductList.value = MeliResult.Loading
        viewModelScope.launch {
            _resultProductList.value = productListRepository.getProductListByQuery(query)
        }
    }

    fun setProductList(productList: List<ProductItem>) {
        _productList.value = productList
    }

}