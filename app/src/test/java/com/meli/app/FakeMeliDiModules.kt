package com.meli.app

import org.koin.core.qualifier.named
import org.koin.dsl.module

internal const val FAKE_PRODUCT_API_SERVICE = "FakeProductApiService"

val meliModulesTest = module {
    single(named(FAKE_PRODUCT_API_SERVICE)) {
        FakeProductApiService()
    }
}