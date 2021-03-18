package com.meli.app.di

import androidx.room.Room
import com.meli.app.data.local.MeliDatabase
import com.meli.app.data.local.SearchRepository
import com.meli.app.data.remote.ProductDataSource
import com.meli.app.network.NetworkProductDataSource
import com.meli.app.data.remote.ProductListRepository
import com.meli.app.data.remote.ProductRepository
import com.meli.app.network.buildRetrofit
import com.meli.app.network.createProductApiService
import com.meli.app.ui.product.ProductViewModel
import com.meli.app.ui.productlist.ProductListViewModel
import com.meli.app.ui.search.SearchViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

internal const val MELI_RETROFIT = "MeliRetrofit"
internal const val PRODUCT_API_SERVICE = "ProductApiService"
internal const val NETWORK_PRODUCT_DATA_SOURCE = "NetworkProductDataSource"
internal const val PRODUCT_LIST_REPOSITORY = "productListRepository"
internal const val PRODUCT_LIST_VIEW_MODEL = "productListViewModel"
internal const val PRODUCT_REPOSITORY = "productRepository"
internal const val PRODUCT_VIEW_MODEL = "productViewModel"
internal const val SEARCH_DAO = "searchDao"
internal const val SEARCH_REPOSITORY = "searchRepository"
internal const val SEARCH_VIEW_MODEL = "searchViewModel"

private const val NAME_DATABASE = "meliDatabase"

val meliModule = module {

    single {
        Room.databaseBuilder(
            androidContext(),
            MeliDatabase::class.java,
            NAME_DATABASE
        ).build()
    }

    single(named(SEARCH_DAO)) { get<MeliDatabase>().searchDao() }

    single(named(SEARCH_REPOSITORY)) { SearchRepository(get(named(SEARCH_DAO))) }

    viewModel(named(SEARCH_VIEW_MODEL)) { SearchViewModel(get(named(SEARCH_REPOSITORY))) }

    single(named(MELI_RETROFIT)) { buildRetrofit() }

    single(named(PRODUCT_API_SERVICE)) { createProductApiService(retrofit = get(named(MELI_RETROFIT))) }

    single<ProductDataSource>(named(NETWORK_PRODUCT_DATA_SOURCE)) {
        NetworkProductDataSource(productApiService = get(named(PRODUCT_API_SERVICE)))
    }

    single(named(PRODUCT_LIST_REPOSITORY)) {
        ProductListRepository(get(named(NETWORK_PRODUCT_DATA_SOURCE)))
    }

    viewModel(named(PRODUCT_LIST_VIEW_MODEL)) {
        ProductListViewModel(get(named(PRODUCT_LIST_REPOSITORY)))
    }

    single(named(PRODUCT_REPOSITORY)) { ProductRepository(get(named(NETWORK_PRODUCT_DATA_SOURCE))) }

    viewModel(named(PRODUCT_VIEW_MODEL)) { ProductViewModel(get(named(PRODUCT_REPOSITORY))) }

}