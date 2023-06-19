package com.jaroidx.navigation.models


import com.google.gson.annotations.SerializedName

data class ListProductResponse(
    @SerializedName("limit")
    val limit: Int,
    @SerializedName("products")
    val products: List<Product>,
    @SerializedName("skip")
    val skip: Int,
    @SerializedName("total")
    val total: Int
)