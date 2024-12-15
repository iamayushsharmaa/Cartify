package com.example.foodorderapp.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodorderapp.repository.firestore.FirestoreRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val firestoreRepository: FirestoreRepositoryImpl
) : ViewModel() {

    private val _itemCount = MutableLiveData<Int>()
    val itemCount: LiveData<Int> = _itemCount

    private val _isCartVisible = MutableLiveData<Boolean>()
    val isCartVisible: LiveData<Boolean> = _isCartVisible

    init {
        loadCartData()
    }

    fun incrementItemCount() {
        val newCount = (itemCount.value ?: 0) + 1
        _itemCount.value = newCount
        firestoreRepository.saveCartItemCount(newCount)
        firestoreRepository.saveIsCartVisible(true)
        _isCartVisible.value = true
    }

    fun decrementItemCount() {
        val newCount = (itemCount.value ?: 0) - 1
        _itemCount.value = newCount
        firestoreRepository.saveCartItemCount(newCount)
        if (newCount == 0) {
            firestoreRepository.saveIsCartVisible(false)
            _isCartVisible.value = false
        }
    }

    private fun loadCartData() {
        _itemCount.value = firestoreRepository.getCartItemCount()
        _isCartVisible.value = firestoreRepository.isCartVisible()
    }
}
