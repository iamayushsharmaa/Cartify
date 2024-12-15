package com.example.foodorderapp.repository.firestore

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.foodorderapp.data.DbResult
import com.example.foodorderapp.data.ProductItemData
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class FirestoreRepositoryImpl  @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val sharedPreferences: SharedPreferences
) : FirestoreRepository {

    private val PREF_CART_ITEM_COUNT = "pref_cart_item_count"
    private val PREF_IS_CART_VISIBLE = "pref_is_cart_visible"

    override suspend fun getAllProduct(): Flow<DbResult<List<ProductItemData>>> = callbackFlow {
        trySend(DbResult.Loading)

        val query = firestore.collection("Products")

        val listenerRegistration = query.addSnapshotListener { snapshot, error ->
            if (error != null) {
                Log.d("getProductsByCategory", "Error: ${error.message}")
                trySend(DbResult.Error("Error: ${error.message}")).isSuccess
                return@addSnapshotListener
            }
            val products = try {
                snapshot?.toObjects(ProductItemData::class.java) ?: emptyList()
            } catch (e: Exception) {
                Log.d("getProductsByCategory", "Mapping Exception: ${e.message}")
                trySend(DbResult.Error("Mapping Exception: ${e.message}")).isSuccess
                emptyList<ProductItemData>()
            }
            trySend(DbResult.Success(products)).isSuccess
        }
        awaitClose {
            listenerRegistration.remove() // Ensure the listener is removed
            Log.d("getProductsByCategory", "Listener removed")
        }
    }

    override suspend fun getCategoryProduct(category : String): Flow<DbResult<List<ProductItemData>>> = callbackFlow {
        trySend(DbResult.Loading)

        val query = if (category == "All") {
            firestore.collection("Products")
        } else {
            firestore.collection("Products").whereEqualTo("productCategory", category)
        }

        val listenerRegistration = query.addSnapshotListener { snapshot, error ->
            if (error != null) {
                Log.d("getProductsByCategory", "Error: ${error.message}")
                trySend(DbResult.Error("Error: ${error.message}")).isSuccess
                return@addSnapshotListener
            }
            val products = try {
                snapshot?.toObjects(ProductItemData::class.java) ?: emptyList()
            } catch (e: Exception) {
                Log.d("getProductsByCategory", "Mapping Exception: ${e.message}")
                trySend(DbResult.Error("Mapping Exception: ${e.message}")).isSuccess
                emptyList<ProductItemData>()
            }
            trySend(DbResult.Success(products)).isSuccess
        }

        awaitClose {
            listenerRegistration.remove() // Ensure the listener is removed
            Log.d("getProductsByCategory", "Listener removed")
        }
    }

    // Firestore related methods (for example)
//    override fun getCartDataFromFirestore(): LiveData<CartData> {
//        // Firestore code to fetch data
//        val cartData = MutableLiveData<CartData>()
//        firestore.collection("cart").document("user_cart")
//            .get()
//            .addOnSuccessListener { document ->
//                val data = document.toObject(CartData::class.java)
//                cartData.postValue(data)
//            }
//        return cartData
//    }

    // Shared Preferences related methods for storing and retrieving cart data
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

    fun isCartVisible(): Boolean {
        return sharedPreferences.getBoolean(PREF_IS_CART_VISIBLE, false) // Default to false if not set
    }

    // Combine Firestore and SharedPreferences: You could sync Firestore and SharedPreferences
//    fun syncCartDataToFirestore(itemCount: Int, isVisible: Boolean) {
//        val cartData = hashMapOf(
//            "itemCount" to itemCount,
//            "isVisible" to isVisible
//        )
//
//        firestore.collection("cart").document("user_cart")
//            .set(cartData)
//            .addOnSuccessListener {
//                // Data successfully synced to Firestore
//            }
//    }

    // Example of using SharedPreferences for local storage and syncing with Firestore
//    fun loadCartData() {
//        val itemCount = getCartItemCount()
//        val isVisible = isCartVisible()
//
//        // Optionally, sync with Firestore
//        syncCartDataToFirestore(itemCount, isVisible)
//    }
}