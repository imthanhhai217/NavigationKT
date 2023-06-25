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
import com.jaroidx.navigation.models.Product
import com.jaroidx.navigation.repositories.ListProductRepository
import com.jaroidx.navigation.repositories.WishListRepository
import com.jaroidx.navigation.ui.home.HomeViewModel
import com.jaroidx.navigation.ui.home.HomeViewModelFactory
import com.jaroidx.navigation.ui.wishlist.WishListViewModeFactory
import com.jaroidx.navigation.ui.wishlist.WishListViewModel

class MainActivity : AppCompatActivity() {
    lateinit var homeViewModel: HomeViewModel
    lateinit var wishListViewModel: WishListViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val view = layoutInflater.inflate(R.layout.activity_main, null)
        val binding = ActivityMainBinding.bind(view)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.findNavController()
        binding.bottomNav.setupWithNavController(navController)
        initHomeViewModel()
        initWishViewModel()
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