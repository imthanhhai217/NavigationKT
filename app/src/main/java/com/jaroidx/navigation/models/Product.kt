package com.jaroidx.navigation.models


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Product(
    @SerializedName("brand") val brand: String,
    @SerializedName("category") val category: String,
    @SerializedName("description") val description: String,
    @SerializedName("discountPercentage") val discountPercentage: Double,
    @SerializedName("id") @PrimaryKey val id: Int,
    @SerializedName("images") val images: List<String>,
    @SerializedName("price") val price: Int,
    @SerializedName("rating") val rating: Double,
    @SerializedName("stock") val stock: Int,
    @SerializedName("thumbnail") val thumbnail: String,
    @SerializedName("title") val title: String,
    var wishlist: Boolean = false
)