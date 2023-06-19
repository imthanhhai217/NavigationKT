package com.jaroidx.navigation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.jaroidx.navigation.R
import com.jaroidx.navigation.databinding.LayoutItemProductBinding
import com.jaroidx.navigation.models.Product

class ListProductAdapter : RecyclerView.Adapter<ListProductAdapter.ProductViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, diffCallback)

    class ProductViewHolder(val binding: LayoutItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product?) {
            binding.product = product
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.layout_item_product, parent, false)
        val binding = LayoutItemProductBinding.bind(view)
        return ProductViewHolder(binding)
    }

    override fun getItemCount() = differ.currentList.size

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }
}