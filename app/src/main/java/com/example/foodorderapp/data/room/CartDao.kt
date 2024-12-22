package com.example.foodorderapp.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.foodorderapp.data.model.CartProduct
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCartProduct(products : CartProduct)

    @Update
    suspend fun updateCartProduct(products: CartProduct)

    @Query("SELECT * FROM CartProduct WHERE productId = :productId LIMIT 1")
    suspend fun getProductById(productId: String): CartProduct?

    @Query("SELECT * FROM cartproduct")
    suspend fun getAllCartProducts(): Flow<List<CartProduct>>

    @Query("DELETE FROM cartproduct WHERE productId = :productId")
    suspend fun deleteCartProductById(productId: String)
}