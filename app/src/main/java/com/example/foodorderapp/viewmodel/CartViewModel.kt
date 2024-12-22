package com.example.foodorderapp.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodorderapp.data.model.CartProduct
import com.example.foodorderapp.data.model.ProductItemData
import com.example.foodorderapp.data.room.CartRepository
import com.example.foodorderapp.repository.firestore.FirestoreRepository
import com.example.foodorderapp.utils.toCartProduct
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartRepository: CartRepository,
    private val firestoreRepository: FirestoreRepository
) : ViewModel() {

    private val _itemCount = MutableLiveData<Int>()
    val itemCount: LiveData<Int> = _itemCount

    private val _isCartVisible = MutableLiveData<Boolean>()
    val isCartVisible: LiveData<Boolean> = _isCartVisible

    private val _cartProducts = MutableStateFlow<List<CartProduct>>(emptyList())
    val cartProducts: StateFlow<List<CartProduct>> get() = _cartProducts

    private fun loadCartProducts() {
        viewModelScope.launch {
            cartRepository.getAllCartProducts().collect { products ->
                _cartProducts.value = products // Update the state
            }
        }
    }
    init {
        loadCartData()
    }

    fun addProductToCart(productItem: ProductItemData) {
        viewModelScope.launch {
            val cartProduct = productItem.toCartProduct().copy(productCount = 1)
            cartRepository.insertCartProduct(cartProduct)
            firestoreRepository.updateProductCountInFirestore(cartProduct.productId, cartProduct.productCount)
            updateCartVisibility()
        }
    }

    fun updateProductCount(productId: String, newCount: Int) {
        viewModelScope.launch {
            if (newCount > 0) {
                cartRepository.updateProductCount(productId, newCount)
                firestoreRepository.updateProductCountInFirestore(productId, newCount)
            } else {
                cartRepository.deleteCartProduct(productId)
                firestoreRepository.updateProductCountInFirestore(productId, 0)
            }
            updateCartVisibility()
        }
    }

    fun incrementItemCount(product: ProductItemData) {
        viewModelScope.launch {
            val cartProduct = cartRepository.getCartProductByProductId(product.productId)
            if (cartProduct != null) {
                val newCount = cartProduct.productCount + 1
                updateProductCount(cartProduct.productId, newCount)
                _itemCount.value = newCount
            } else {
                addProductToCart(product) // Add product to cart if not present
            }
        }
    }

    fun decrementItemCount(product: ProductItemData) {
        viewModelScope.launch {
            val cartProduct = cartRepository.getCartProductByProductId(product.productId)
            if (cartProduct != null) {
                val newCount = (cartProduct.productCount - 1).coerceAtLeast(0)
                updateProductCount(cartProduct.productId, newCount)
                _itemCount.value = newCount
            }
        }
    }

    private fun loadCartData() {
        viewModelScope.launch {
            val totalCount = cartRepository.getCartItemCount()
            _itemCount.postValue(totalCount)
            _isCartVisible.postValue(totalCount > 0)
        }
    }

    private fun updateCartVisibility() {
        val totalCount = cartRepository.getCartItemCount()
        _isCartVisible.postValue(totalCount > 0)
    }
}
