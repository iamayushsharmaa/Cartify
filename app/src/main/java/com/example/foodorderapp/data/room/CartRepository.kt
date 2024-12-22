package com.example.foodorderapp.data.room

import android.content.SharedPreferences
import android.util.Log
import com.example.foodorderapp.data.model.CartProduct
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class CartRepository @Inject constructor(
    private val cartDao: CartDao,
    private val sharedPreferences: SharedPreferences,
) {

    private val PREF_CART_ITEM_COUNT = "pref_cart_item_count"
    private val PREF_IS_CART_VISIBLE = "pref_is_cart_visible"

    suspend fun getAllCartProducts() : Flow<List<CartProduct>> {
        return cartDao.getAllCartProducts()
    }

    suspend fun insertCartProduct(cartProduct: CartProduct) {
        cartDao.insertCartProduct(cartProduct)
    }


    suspend fun updateCartProduct(cartProduct: CartProduct) {
        cartDao.updateCartProduct(cartProduct)
    }

    suspend fun deleteCartProduct(productId: String) {
        cartDao.deleteCartProductById(productId)
    }

    suspend fun getCartProductByProductId(productId: String): CartProduct? {
        return cartDao.getProductById(productId)
    }
    suspend fun updateProductCount(productId: String, newCount: Int) {
        val cartProduct = cartDao.getAllCartProducts().firstOrNull()?.find { it.productId == productId }
        if (cartProduct != null) {
            val updatedProduct = cartProduct.copy(productCount = newCount)
            if (newCount > 0) {
                cartDao.updateCartProduct(updatedProduct)
            } else {
                cartDao.deleteCartProductById(productId)
            }
        }
    }

    fun saveCartItemCount(count: Int) {
        sharedPreferences.edit()
            .putInt(PREF_CART_ITEM_COUNT, count)
            .apply()
    }

    fun getCartItemCount(): Int {
        return sharedPreferences.getInt(PREF_CART_ITEM_COUNT, 0) // Default to 0 if not set
    }

    fun saveIsCartVisible(isVisible: Boolean) {
        sharedPreferences.edit()
            .putBoolean(PREF_IS_CART_VISIBLE, isVisible)
            .apply()
    }



}
