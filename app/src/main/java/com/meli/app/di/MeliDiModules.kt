package com.meli.app.di

import com.meli.app.data.ProductDataSource
import com.meli.app.network.NetworkProductDataSource
import com.meli.app.data.ProductListRepository
import com.meli.app.network.buildRetrofit
import com.meli.app.network.createProductApiService
import com.meli.app.ui.productlist.ProductListViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

internal const val MELI_RETROFIT = "MeliRetrofit"
internal const val PRODUCT_API_SERVICE = "ProductApiService"
internal const val NETWORK_PRODUCT_DATA_SOURCE = "NetworkProductDataSource"
internal const val PRODUCT_REPOSITORY = "productRepository"
internal const val PRODUCT_VIEW_MODEL = "productViewModel"

val meliModule = module {

    single(named(MELI_RETROFIT)) { buildRetrofit() }

    single(named(PRODUCT_API_SERVICE)) { createProductApiService(retrofit = get(named(MELI_RETROFIT))) }

    single<ProductDataSource>(named(NETWORK_PRODUCT_DATA_SOURCE)) {
        NetworkProductDataSource(productApiService = get(named(PRODUCT_API_SERVICE)))
    }

    single(named(PRODUCT_REPOSITORY)) { ProductListRepository(get(named(NETWORK_PRODUCT_DATA_SOURCE))) }

    viewModel(named(PRODUCT_VIEW_MODEL)) { ProductListViewModel(get(named(PRODUCT_REPOSITORY))) }

}