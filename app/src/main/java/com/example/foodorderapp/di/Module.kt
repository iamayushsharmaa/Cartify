package com.example.foodorderapp.di

import android.content.Context
import android.content.SharedPreferences
import com.example.foodorderapp.repository.firebase.FirebaseRepository
import com.example.foodorderapp.repository.firebase.FirebaseRepositoryImpl
import com.example.foodorderapp.repository.firestore.FirestoreRepository
import com.example.foodorderapp.repository.firestore.FirestoreRepositoryImpl
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Module {

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("cart_preferences", Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()

    @Provides
    @Singleton
    fun provideFirebaseRepository(): FirebaseRepository {
        return FirebaseRepositoryImpl()
    }
    @Provides
    @Singleton
    fun provideFirestoreRepository(
        firestore: FirebaseFirestore,
    ): FirestoreRepository {
        return FirestoreRepositoryImpl(firestore )
    }
}