package com.example.foodorderapp.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.foodorderapp.data.model.CartProduct

@Database(entities = [CartProduct::class], version = 1, exportSchema = false)
abstract class CartDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao
}