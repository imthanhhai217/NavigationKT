package com.jaroidx.navigation.database.wishlist

import androidx.lifecycle.LiveData
import androidx.room.*
import com.jaroidx.navigation.models.Product

@Dao
interface WishDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(product: Product)

    @Delete
    suspend fun delete(product: Product)

    @Query("SELECT * FROM Product")
    fun getAllWishList(): LiveData<List<Product>>
}