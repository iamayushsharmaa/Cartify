@file:Suppress("UNREACHABLE_CODE")

package com.example.foodorderapp.view.main

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.foodorderapp.R
import com.example.foodorderapp.data.DbResult
import com.example.foodorderapp.view.main.components.ItemDesign
import com.example.foodorderapp.view.main.components.ShimmerItemBox
import com.example.foodorderapp.viewmodel.CartViewModel
import com.example.foodorderapp.viewmodel.FirestoreViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    firestoreViewModel: FirestoreViewModel,
    cartViewModel: CartViewModel = viewModel()
) {
    var searchProduct by remember { mutableStateOf("") }
    val productState by firestoreViewModel.productState.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { /* Handle back navigation */ }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    OutlinedTextField(
                        value = searchProduct,
                        onValueChange = { searchProduct = it },
                        label = { Text("Search") },
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .padding(end = 8.dp),
                        placeholder = { Text(text = "Search Product") },
                        leadingIcon = {
                            Icon(
                                painter = painterResource(
                                    id = if (isSystemInDarkTheme()) R.drawable.icon_search_white
                                    else R.drawable.icon_search_black
                                ),
                                contentDescription = "Search Icon"
                            )
                        },
                        trailingIcon = {
                            // Divider and search icon
                            Divider(
                                modifier = Modifier
                                    .height(36.dp)
                                    .width(1.dp),
                                color = Color.Gray
                            )
                            Spacer(modifier = Modifier.width(8.dp)) // To provide space between divider and icon
                            Icon(
                                painter = painterResource(
                                    id = if (isSystemInDarkTheme()) R.drawable.icon_microphone_white
                                    else R.drawable.icon_microphone
                                ),
                                contentDescription = "voice Search Icon",
                                modifier = Modifier
                                    .padding(start = 5.dp, top = 5.dp)
                            )
                        },
                        shape = RoundedCornerShape(18.dp),
                        singleLine = true
                    )
                },
                title = { Text("Search") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color.Black)
        ) {
            when (productState) {
                is DbResult.Loading -> {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 8.dp),
                        contentPadding = PaddingValues(4.dp)
                    ) {
                        items(6) { // Show 6 shimmer items as placeholders
                            ShimmerItemBox(
                                isLoading = true,
                                contentAfterLoading = {}
                            )
                        }
                    }

                }
                is DbResult.Success -> {
                    val filteredProducts by firestoreViewModel.filteredProducts.collectAsState()

                    if (filteredProducts.isNullOrEmpty()) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "No Products",
                                fontSize = 25.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.White
                            )
                        }
                    }else{
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 8.dp, vertical = 8.dp),
                            contentPadding = PaddingValues(4.dp)
                        ) {
                            items(filteredProducts){ product->
                                ItemDesign(
                                    product,
                                    onProductAddClick = {
                                       cartViewModel.incrementItemCount()
                                    },
                                    cartViewModel

                                )
//                                if (isDialogVisible) {
//                                    EditProductScreen(
//                                        product,
//                                        onDismiss = { isDialogVisible = false },
//                                        firestoreViewModel,
//                                        navController,
//                                        activity
//                                    )
//                                }
                            }

                        }
                    }
                }
                is DbResult.Error -> {
                    val errorMessage = (productState as DbResult.Error).message
                    Text(text = errorMessage, color = Color.Red)
                }
                DbResult.Idle -> {
                    Box(modifier = Modifier
                        .fillMaxSize(),

                        )
                }
            }
        }
    }
}

@Preview
@Composable
private fun SearchPrev() {

    SearchScreen(viewModel())
}

