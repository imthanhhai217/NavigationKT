package com.jaroidx.navigation.utils

import android.widget.ImageView
import com.bumptech.glide.Glide

object BindingAdapter {

    @JvmStatic
    @androidx.databinding.BindingAdapter("loadImage")
    fun ImageView.loadImage(url:String){
        url.let {
            Glide.with(this).load(it).into(this)
        }
    }
}