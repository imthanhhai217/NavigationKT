package com.jaroidx.navigation.utils

object StringUtils {

    @JvmStatic
    fun getPricesString(price: Int): String {
        return "$$price"
    }

    @JvmStatic
    fun convert2String(number: Double): String {
        return "$number"
    }

    @JvmStatic
    fun convert2String(number: Int): String {
        return "$number"
    }
}