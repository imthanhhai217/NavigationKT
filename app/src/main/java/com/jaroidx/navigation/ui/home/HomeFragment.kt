package com.jaroidx.navigation.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.jaroidx.navigation.R
import com.jaroidx.navigation.adapters.ListProductAdapter
import com.jaroidx.navigation.api.`object`.BaseResponse
import com.jaroidx.navigation.databinding.FragmentHomeBinding
import com.jaroidx.navigation.models.ListProductResponse
import com.jaroidx.navigation.ui.MainActivity
import com.jaroidx.navigation.utils.BindingAdapter.reverse

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var listProductAdapter: ListProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
        val homeViewModel = (activity as MainActivity).homeViewModel
        initView()

        homeViewModel.isLoading.observe(viewLifecycleOwner, Observer {
            binding.isLoading = it
        })

        homeViewModel.getListProduct()
        homeViewModel.listProductResult.observe(viewLifecycleOwner, Observer {
            when (it) {
                is BaseResponse.Success -> {
                    hideLoading()
                    it.data?.let { listProductResponse ->
                        bindData(listProductResponse)
                    }
                }

                is BaseResponse.Error -> {
                    hideLoading()
                    it.data?.let { body ->
                        showPopup(body, it.message)
                    }
                }

                is BaseResponse.Loading -> {
                    showLoading()
                }
            }
        })
    }

    private fun initView() {
        listProductAdapter = ListProductAdapter(iClickItemListener)
        binding.rvListProduct.apply {
            adapter = listProductAdapter
            layoutManager = GridLayoutManager(context, 2)
        }
    }

    private val iClickItemListener = object : ListProductAdapter.IClickItemListener {
        override fun changeWishStateListener(pos: Int) {
            val product = listProductAdapter.differ.currentList[pos]
            listProductAdapter.differ.currentList[pos].wishlist = product.wishlist.reverse()
            listProductAdapter.differ.submitList(listProductAdapter.differ.currentList)
            listProductAdapter.notifyItemChanged(pos,product)
            if (product.wishlist){
                (activity as MainActivity).wishListViewModel.upsertWish(product)
            }else{
                (activity as MainActivity).wishListViewModel.deleteWish(product)
            }
        }

        override fun showDetailsListener(pos: Int) {

        }
    }

    private fun showLoading() {

    }

    private fun hideLoading() {

    }

    private fun showPopup(data: ListProductResponse, message: String?) {

    }

    private fun bindData(data: ListProductResponse) {
        if (data != null) {
            listProductAdapter.differ.submitList(data.products)
        }
    }
}