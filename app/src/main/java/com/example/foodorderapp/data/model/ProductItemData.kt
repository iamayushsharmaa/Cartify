package com.example.foodorderapp.data.model

import android.net.Uri
import androidx.compose.runtime.Stable

@Stable
data class ProductItemData (
    val productId : String,
    val adminUid : String,
    val productTitle: String,
    val quantityInKg: String,
    val priceInRs: String,
    val unit: String,
    val productCount : Int,
    val noOfStocks: String,
    val productCategory: String,
    val productType: String,
    val productImages: List<Uri>,
    val productRating : Float? = null
){
    fun isMatchWithQuery(queryString: String): Boolean {
        return listOf(
            productTitle,
            productCategory,
            productType
        ).any { it.contains(queryString, ignoreCase = true) }
    }
}