package com.jaroidx.navigation.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.jaroidx.navigation.R
import com.jaroidx.navigation.database.wishlist.WishListDatabase
import com.jaroidx.navigation.databinding.ActivityMainBinding
import com.jaroidx.navigation.repositories.CategoriesRepository
import com.jaroidx.navigation.repositories.ListProductRepository
import com.jaroidx.navigation.repositories.WishListRepository
import com.jaroidx.navigation.ui.account.AccountViewModel
import com.jaroidx.navigation.ui.account.AccountViewModelFactory
import com.jaroidx.navigation.ui.category.CategoriesViewModel
import com.jaroidx.navigation.ui.category.CategoriesViewModelFactory
import com.jaroidx.navigation.ui.home.HomeViewModel
import com.jaroidx.navigation.ui.home.HomeViewModelFactory
import com.jaroidx.navigation.ui.wishlist.WishListViewModeFactory
import com.jaroidx.navigation.ui.wishlist.WishListViewModel

class MainActivity : AppCompatActivity() {
    lateinit var homeViewModel: HomeViewModel
    lateinit var wishListViewModel: WishListViewModel
    lateinit var accountViewModel: AccountViewModel
    lateinit var categoriesViewModel: CategoriesViewModel
    override fun onStart() {
        super.onStart()
    }

    private fun initFirebase() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val view = layoutInflater.inflate(R.layout.activity_main, null)
        val binding = ActivityMainBinding.bind(view)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.findNavController()
        binding.bottomNav.setupWithNavController(navController)

        initFirebase()
        initHomeViewModel()
        initWishViewModel()
        initAccountViewModel()
        initCategoriesViewModel()
    }

    private fun initCategoriesViewModel() {
        val categoriesRepository = CategoriesRepository()
        val categoriesViewModelFactory = CategoriesViewModelFactory(categoriesRepository,application)
        categoriesViewModel =
            ViewModelProvider(this, categoriesViewModelFactory)[CategoriesViewModel::class.java]
    }

    private fun initAccountViewModel() {
        val accountViewModelFactory = AccountViewModelFactory(application)
        accountViewModel =
            ViewModelProvider(this, accountViewModelFactory)[AccountViewModel::class.java]
    }

    private fun initWishViewModel() {
        val wishListDatabase = WishListDatabase(this)
        val wishListRepository = WishListRepository(wishListDatabase)
        val wishListViewModeFactory = WishListViewModeFactory(wishListRepository, application)
        wishListViewModel =
            ViewModelProvider(this, wishListViewModeFactory)[WishListViewModel::class.java]
    }

    private fun initHomeViewModel() {
        val listProductRepository = ListProductRepository()
        val homeViewModelFactory = HomeViewModelFactory(listProductRepository, application)
        homeViewModel = ViewModelProvider(this, homeViewModelFactory)[HomeViewModel::class.java]
    }
}