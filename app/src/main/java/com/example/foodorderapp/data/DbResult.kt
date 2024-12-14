package com.example.foodorderapp.data


sealed class DbResult<out T> {
    object Idle : DbResult<Nothing>()
    object Loading : DbResult<Nothing>()
    data class Success<out T>(val data: T) : DbResult<T>()
    data class Error(val message: String) : DbResult<Nothing>()
}