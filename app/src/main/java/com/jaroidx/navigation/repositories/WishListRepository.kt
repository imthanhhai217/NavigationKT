package com.jaroidx.navigation.repositories

import com.jaroidx.navigation.database.wishlist.WishListDatabase
import com.jaroidx.navigation.models.Product

class WishListRepository(private val wishListDatabase: WishListDatabase) {

    suspend fun upsert(product: Product) = wishListDatabase.getWishDao().upsert(product)

    suspend fun delete(product: Product) = wishListDatabase.getWishDao().delete(product)

    fun getAllWishList() = wishListDatabase.getWishDao().getAllWishList()
}