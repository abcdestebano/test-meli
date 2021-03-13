package com.meli.app.ui.productlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meli.app.data.ProductListRepository
import com.meli.app.model.ProductItem
import com.meli.app.utils.MeliResult
import kotlinx.coroutines.launch

class ProductListViewModel(private val productListRepository: ProductListRepository) : ViewModel() {

    private val _productList: MutableLiveData<MeliResult<List<ProductItem>>> = MutableLiveData()
    val productList: LiveData<MeliResult<List<ProductItem>>> = _productList

    fun getListProductsByQuery(query: String) {
        _productList.value = MeliResult.Loading
        viewModelScope.launch {
            _productList.value = productListRepository.getProductListByQuery(query)
        }
    }

}