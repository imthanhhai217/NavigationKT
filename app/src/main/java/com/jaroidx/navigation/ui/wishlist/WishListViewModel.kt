package com.jaroidx.navigation.ui.wishlist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.jaroidx.navigation.models.Product
import com.jaroidx.navigation.repositories.WishListRepository
import kotlinx.coroutines.launch

class WishListViewModel(
    private val wishListRepository: WishListRepository, application: Application
) : AndroidViewModel(application) {

    fun getAllWishList() = wishListRepository.getAllWishList()

    fun upsertWish(product: Product) = viewModelScope.launch {
        wishListRepository.upsert(product)
    }

    fun deleteWish(product: Product) = viewModelScope.launch {
        wishListRepository.delete(product)
    }
}