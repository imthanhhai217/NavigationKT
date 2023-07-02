package com.jaroidx.navigation.repositories

import com.jaroidx.navigation.api.RetrofitClient

class CategoriesRepository {

    suspend fun getAllCategories() = RetrofitClient.getDummyApi.getAllCategories()
}