package com.meli.app.ui.product

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meli.app.data.ProductRepository
import com.meli.app.model.Product
import com.meli.app.utils.MeliResult
import kotlinx.coroutines.launch

class ProductViewModel(private val productRepository: ProductRepository) : ViewModel() {

    private val _product: MutableLiveData<MeliResult<Product>> = MutableLiveData()
    val product: LiveData<MeliResult<Product>> = _product

    fun getProductById(productId: String) {
        viewModelScope.launch {
            _product.value = productRepository.getProductById(productId)
        }
    }

}