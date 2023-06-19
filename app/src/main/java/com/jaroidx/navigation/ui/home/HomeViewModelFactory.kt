package com.jaroidx.navigation.ui.home

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jaroidx.navigation.repositories.ListProductRepository

class HomeViewModelFactory(
    private val listProductRepository: ListProductRepository,
    private val application: Application
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(listProductRepository, application) as T
    }
}