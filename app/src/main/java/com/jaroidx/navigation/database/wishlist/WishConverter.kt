package com.jaroidx.navigation.database.wishlist

import androidx.room.TypeConverter

class WishConverter {
    @TypeConverter
    fun formImages(images: List<String>): String {
        var result = ""
        for (i in images.indices) {
            result += "${images[i]}"
            if (i < images.size - 1) {
                result += ","
            }
        }
        return images.toString()
    }

    @TypeConverter
    fun toImages(images: String): List<String> {
        return images.split(",")
    }
}