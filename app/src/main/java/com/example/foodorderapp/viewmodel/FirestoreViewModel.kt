package com.example.foodorderapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodorderapp.data.model.DbResult
import com.example.foodorderapp.data.model.ProductItemData
import com.example.foodorderapp.repository.firestore.FirestoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FirestoreViewModel @Inject constructor(
    private val firestoreRepository: FirestoreRepository
) : ViewModel(){

    private val _productState = MutableStateFlow<DbResult<List<ProductItemData>>>(DbResult.Idle)
    val productState = _productState.asStateFlow()

    private val _categoryProductState = MutableStateFlow<DbResult<List<ProductItemData>>>(DbResult.Idle)
    val categoryProductState = _categoryProductState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _filteredProducts = MutableStateFlow<List<ProductItemData>>(emptyList())
    val filteredProducts: StateFlow<List<ProductItemData>> = _filteredProducts.asStateFlow()

    private val _filteredCategoryProducts = MutableStateFlow<List<ProductItemData>>(emptyList())
    val filteredCategoryProducts: StateFlow<List<ProductItemData>> = _filteredCategoryProducts.asStateFlow()

    init {
        getAllProductData()
        observeSearchQuery()
    }

    private fun observeSearchQuery() {
        viewModelScope.launch {
            combine(
                productState.map { state ->
                    Log.d("data", "observeSearchQuery: ${state} ")
                    if (state is DbResult.Success) state.data else emptyList()
                },
                searchQuery
            ) { products, query ->
                filterProducts(products, query)
            }.collect { filteredList ->
                _filteredProducts.value = filteredList
                Log.d("data", "observeSearchQuery: ${_filteredProducts.value} ")
            }
        }
    }

    private fun filterProducts(
        products: List<ProductItemData>,
        query: String
    ): List<ProductItemData> {
        if (query.isBlank()) return products
        return products.filter { it.isMatchWithQuery(query) }
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun getAllProductData(){
        viewModelScope.launch {
            firestoreRepository.getAllProduct().collect { state->
                _productState.value = state
            }
        }
    }

    fun getCategoryProduct(category : String){
        viewModelScope.launch {
            firestoreRepository.getCategoryProduct(category).collect{ state->
                _categoryProductState.value = state
            }
        }
    }


}