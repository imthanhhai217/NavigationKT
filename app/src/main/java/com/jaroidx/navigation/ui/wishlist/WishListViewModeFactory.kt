package com.jaroidx.navigation.ui.wishlist

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jaroidx.navigation.repositories.WishListRepository

class WishListViewModeFactory(private val wishListRepository: WishListRepository,private val application: Application):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return WishListViewModel(wishListRepository, application) as T
    }
}