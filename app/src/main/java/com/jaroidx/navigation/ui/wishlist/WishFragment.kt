package com.jaroidx.navigation.ui.wishlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.jaroidx.navigation.R
import com.jaroidx.navigation.adapters.ListProductAdapter
import com.jaroidx.navigation.database.wishlist.WishListDatabase
import com.jaroidx.navigation.databinding.FragmentWishBinding
import com.jaroidx.navigation.models.Product
import com.jaroidx.navigation.ui.MainActivity

class WishFragment : Fragment() {
    private val TAG = "WishFragment"
    lateinit var binding: FragmentWishBinding
    lateinit var wishListViewModel: WishListViewModel
    private lateinit var mListWish: List<Product>
    private lateinit var mProductAdapter: ListProductAdapter
    private lateinit var mWishListDatabase:WishListDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_wish, container, false)
        binding = FragmentWishBinding.bind(view)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        wishListViewModel = (activity as MainActivity).wishListViewModel
        initView()
        wishListViewModel.getAllWishList().observe(viewLifecycleOwner, Observer {
            mListWish = it
            bindWishData(mListWish)
        })
    }

    private fun initView() {
        mProductAdapter = ListProductAdapter(iClickItemListener)
        binding.rvWishList.adapter = mProductAdapter
        binding.rvWishList.layoutManager = GridLayoutManager(activity, 2)
    }

    private val iClickItemListener = object : ListProductAdapter.IClickItemListener {
        override fun changeWishStateListener(pos: Int) {
            var product = mProductAdapter.differ.currentList[pos]
            if (product.wishlist){
                (activity as MainActivity).wishListViewModel.deleteWish(product)
            }
        }

        override fun showDetailsListener(pos: Int) {

        }
    }

    private fun bindWishData(mListWish: List<Product>?) {
        if (mListWish != null) {
            mProductAdapter.differ.submitList(mListWish)
        }
    }
}