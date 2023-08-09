package com.jaroidx.navigation.ui

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationBarView
import com.google.android.material.navigation.NavigationView
import com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener
import com.jaroidx.navigation.R
import com.jaroidx.navigation.database.wishlist.WishListDatabase
import com.jaroidx.navigation.databinding.ActivityMainBinding
import com.jaroidx.navigation.repositories.CategoriesRepository
import com.jaroidx.navigation.repositories.ListProductRepository
import com.jaroidx.navigation.repositories.WishListRepository
import com.jaroidx.navigation.ui.account.AccountViewModel
import com.jaroidx.navigation.ui.account.AccountViewModelFactory
import com.jaroidx.navigation.ui.calendar.CalendarFragment
import com.jaroidx.navigation.ui.category.CategoriesViewModel
import com.jaroidx.navigation.ui.category.CategoriesViewModelFactory
import com.jaroidx.navigation.ui.home.HomeFragment
import com.jaroidx.navigation.ui.home.HomeViewModel
import com.jaroidx.navigation.ui.home.HomeViewModelFactory
import com.jaroidx.navigation.ui.mission.MissionFragment
import com.jaroidx.navigation.ui.my.MyFragment
import com.jaroidx.navigation.ui.wishlist.WishListViewModeFactory
import com.jaroidx.navigation.ui.wishlist.WishListViewModel

class MainActivity : AppCompatActivity(), OnNavigationItemSelectedListener {

    lateinit var binding: ActivityMainBinding
    lateinit var homeViewModel: HomeViewModel
    lateinit var wishListViewModel: WishListViewModel
    lateinit var accountViewModel: AccountViewModel
    lateinit var categoriesViewModel: CategoriesViewModel
    private lateinit var navController: NavController
    private lateinit var drawerLayout: DrawerLayout
    override fun onStart() {
        super.onStart()
    }

    private fun initFirebase() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val view = layoutInflater.inflate(R.layout.activity_main, null)
        binding = ActivityMainBinding.bind(view)
        setContentView(binding.root)

        initFirebase()
        initHomeViewModel()
        initWishViewModel()
        initAccountViewModel()
        initCategoriesViewModel()

        //Drawer
        val navControllerDrawer = findNavController(R.id.nav_host_fragment_drawer)
        setupActionBarWithNavController(navControllerDrawer)
        val navView: NavigationView = binding.navView
        navView.setupWithNavController(navControllerDrawer)
        val toolbar = binding.toolbar
        toolbar.visibility = View.GONE
        drawerLayout = binding.drawerLayout
        val actionBarDrawerToggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer
        )
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
        navView.setNavigationItemSelectedListener(this)
        binding.btnDrawer.setOnClickListener {
            drawerLayout.visibility = View.VISIBLE
            drawerLayout.openDrawer(GravityCompat.START)
        }

        //BottomNavigation
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.findNavController()
        binding.bottomNav.setupWithNavController(navController)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_mission -> {
                //go to mission
            }
            R.id.nav_calender -> {
                //go to calender
            }
            R.id.nav_my -> {
                //go to my
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onResume() {
        super.onResume()
        val intent = intent
        val open = intent.getStringExtra("open")
        open?.toInt().let {
            when (it) {
                0 -> {
                    return
                }
                1 -> {
                    binding.bottomNav.selectedItemId = R.id.wishFragment
                }
                2 -> {
                    binding.bottomNav.selectedItemId = R.id.categoriesFragment
                }
                3 -> {
                    binding.bottomNav.selectedItemId = R.id.accountFragment
                }
                4 -> {
                    binding.bottomNav.selectedItemId = R.id.cartFragment
                }
            }
        }
    }

    private fun initCategoriesViewModel() {
        val categoriesRepository = CategoriesRepository()
        val categoriesViewModelFactory =
            CategoriesViewModelFactory(categoriesRepository, application)
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