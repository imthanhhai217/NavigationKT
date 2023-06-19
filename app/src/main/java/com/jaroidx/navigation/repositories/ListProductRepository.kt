package com.jaroidx.navigation.repositories

import com.jaroidx.navigation.api.RetrofitClient

class ListProductRepository {

    suspend fun getListProduct() = RetrofitClient.getDummyApi.getListProduct()

    suspend fun getListProduct(limit: String) = RetrofitClient.getDummyApi.getListProduct(limit)
}