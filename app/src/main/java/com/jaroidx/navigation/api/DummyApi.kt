package com.jaroidx.navigation.api

import com.jaroidx.navigation.models.ListProductResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface DummyApi {

    @GET("/products")
    suspend fun getListProduct():Response<ListProductResponse>

    @GET("/products")
    suspend fun getListProduct(@Query("limit") limit: String):Response<ListProductResponse>
}