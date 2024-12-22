package com.example.foodorderapp.utils

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

//@Module
//@InstallIn(SingletonComponent::class)
//object DatabaseModule {
//
//    @Provides
//    @Singleton
//    fun provideDatabase(@ApplicationContext context: Context): CartDatabase {
//        return Room.databaseBuilder(
//            context,
//            CartDatabase::class.java,
//            "app_database"
//        ).build()
//    }
//
//    @Provides
//    fun provideCartDao(appDatabase: CartDatabase): CartDao {
//        return appDatabase.cartDao()
//    }
//}
