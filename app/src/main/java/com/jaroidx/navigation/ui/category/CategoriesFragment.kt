package com.jaroidx.navigation.ui.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.jaroidx.navigation.R
import com.jaroidx.navigation.api.`object`.BaseResponse
import com.jaroidx.navigation.databinding.FragmentCategoriesBinding
import com.jaroidx.navigation.models.CategoriesResponse
import com.jaroidx.navigation.ui.MainActivity

class CategoriesFragment : Fragment() {

    lateinit var categoriesViewModel: CategoriesViewModel
    lateinit var binding: FragmentCategoriesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_categories, container, false)
        binding = FragmentCategoriesBinding.bind(view)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        categoriesViewModel = (activity as MainActivity).categoriesViewModel

        categoriesViewModel.saveMessage("Hello im hai")

        (activity as MainActivity).wishListViewModel.getAllWishList()
            .observe(viewLifecycleOwner, Observer {
                if (it != null) {
                    for (i in it) {
//                    categoriesViewModel.saveProduct(i)

                        categoriesViewModel.getProducts()
                    }
                }
            })

        categoriesViewModel.categoriesResult.observe(viewLifecycleOwner, Observer {
            when (it) {
                is BaseResponse.Loading -> {

                }
                is BaseResponse.Success -> {
                    it.data?.let { it1 -> updateDataToFirebase(it1) }
                }
                is BaseResponse.Error -> {

                }
            }
        })

    }

    private fun updateDataToFirebase(it: CategoriesResponse) {
        categoriesViewModel.saveCategory(it)
    }
}