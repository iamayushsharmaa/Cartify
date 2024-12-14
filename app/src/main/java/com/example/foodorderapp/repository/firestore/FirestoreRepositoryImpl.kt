package com.example.foodorderapp.repository.firestore

import android.util.Log
import com.example.foodorderapp.data.DbResult
import com.example.foodorderapp.data.ProductItemData
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class FirestoreRepositoryImpl  @Inject constructor(
    private val firestore: FirebaseFirestore
) : FirestoreRepository {
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
}