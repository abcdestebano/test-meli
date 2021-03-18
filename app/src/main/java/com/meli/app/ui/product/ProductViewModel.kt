package com.meli.app.ui.product

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meli.app.data.remote.ProductRepository
import com.meli.app.model.Product
import com.meli.app.utils.MeliResult
import kotlinx.coroutines.launch

class ProductViewModel(private val productRepository: ProductRepository) : ViewModel() {

    private val _product: MutableLiveData<MeliResult<Product>> = MutableLiveData()
    val product: LiveData<MeliResult<Product>> = _product

    private val _productId: MutableLiveData<String> = MutableLiveData()
    val productId: LiveData<String> = _productId

    fun getProductById(productId: String) {
        _product.value = MeliResult.Loading
        _productId.value = productId
        viewModelScope.launch {
            _product.value = productRepository.getProductById(productId)
        }
    }

}