package com.jaroidx.navigation.ui.category

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jaroidx.navigation.repositories.CategoriesRepository

class CategoriesViewModelFactory(private val categoriesRepository:CategoriesRepository,private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CategoriesViewModel(categoriesRepository,application) as T
    }
}