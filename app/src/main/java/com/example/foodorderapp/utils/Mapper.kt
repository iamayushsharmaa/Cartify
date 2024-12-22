package com.example.foodorderapp.utils

import android.net.Uri
import com.example.foodorderapp.data.model.CartProduct
import com.example.foodorderapp.data.model.ProductItemData

fun ProductItemData.toCartProduct(): CartProduct {
    return CartProduct(
        productId = this.productId,
        adminUid = this.adminUid,
        productTitle = this.productTitle,
        quantityInKg = this.quantityInKg.toIntOrNull(),
        priceInRs = this.priceInRs.toIntOrNull(),
        productCount = this.productCount,
        unit = this.unit,
        noOfStocks = this.noOfStocks.toIntOrNull(),
        productCategory = this.productCategory,
        productType = this.productType,
        productImages = this.productImages.firstOrNull()?.toString() // Convert URI to String
    )
}


fun CartProduct.toProductItemData(): ProductItemData {
    return ProductItemData(
        productId = this.productId,
        adminUid = this.adminUid,
        productTitle = this.productTitle,
        quantityInKg = this.quantityInKg?.toString() ?: "0",
        priceInRs = this.priceInRs?.toString() ?: "0",
        unit = this.unit ?: "",
        productCount = this.productCount,
        noOfStocks = this.noOfStocks?.toString() ?: "0",
        productCategory = this.productCategory ?: "",
        productType = this.productType ?: "",
        productImages = this.productImages?.split(",")?.map { Uri.parse(it) } ?: emptyList(),
        productRating = null
    )
}