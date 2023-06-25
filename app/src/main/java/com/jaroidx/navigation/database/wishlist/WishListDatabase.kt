package com.jaroidx.navigation.database.wishlist

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jaroidx.navigation.models.Product
import com.jaroidx.navigation.utils.Constants

@androidx.room.Database(entities = [Product::class], version = 1)
@TypeConverters(WishConverter::class)
abstract class WishListDatabase : RoomDatabase() {

    abstract fun getWishDao(): WishDao

    companion object {
        private var instance: WishListDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also {
                instance = it
            }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(context, WishListDatabase::class.java, Constants.WISHLIST_DB)
                .build()
    }
}