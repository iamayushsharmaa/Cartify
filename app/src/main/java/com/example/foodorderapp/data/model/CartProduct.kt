package com.example.foodorderapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "CartProduct")
data class CartProduct(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val productId: String,
    val adminUid: String,
    val productTitle: String,
    val quantityInKg: Int?,
    val priceInRs: Int?,
    val productCount: Int,
    val unit: String?,
    val noOfStocks: Int?,
    val productCategory: String?,
    val productType: String?,
    val productImages: String?

)
