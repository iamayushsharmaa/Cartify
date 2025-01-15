@file:Suppress("UNREACHABLE_CODE")

package com.example.foodorderapp.view.main.bottomnav

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.foodorderapp.R
import com.example.foodorderapp.data.model.DbResult
import com.example.foodorderapp.ui.theme.poppinsFontFamily
import com.example.foodorderapp.view.main.components.ItemDesign
import com.example.foodorderapp.view.main.components.ShimmerItemBox
import com.example.foodorderapp.viewmodel.CartViewModel
import com.example.foodorderapp.viewmodel.FirestoreViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Search(
    firestoreViewModel: FirestoreViewModel,
    cartViewModel: CartViewModel = hiltViewModel()
) {
    var searchProduct by remember { mutableStateOf("") }
    val productState by firestoreViewModel.productState.collectAsState()

    val isCartVisible by cartViewModel.isCartVisible.observeAsState(false)
    val itemCount by cartViewModel.itemCount.observeAsState(0)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
            .background(Color.White)
    ) {
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = "Search",
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black,
                fontSize = 28.sp
            )
        }

        OutlinedTextField(
            value = searchProduct,
            onValueChange = {
                searchProduct = it
                firestoreViewModel.updateSearchQuery(searchProduct)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, start = 8.dp, end = 8.dp, bottom = 8.dp),
            placeholder = {
                Text(
                    text = "Search...",
                    fontFamily = poppinsFontFamily,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal
                )
            },
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
                Divider(
                    modifier = Modifier
                        .height(36.dp)
                        .width(1.dp),
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.width(14.dp)) // To provide space between divider and icon
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
            shape = RoundedCornerShape(20.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xFFD4D4D4),
                unfocusedContainerColor =  Color(0xFFD4D4D4),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = Color.Black,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black
                ),
            singleLine = true
        )
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
                                    cartViewModel.addProductToCart(product)
                                    cartViewModel.incrementItemCount(product)
                                },
                                onIncrementClick = {
                                    cartViewModel.incrementItemCount(product)
                                },
                                onDecrementClick = {
                                    cartViewModel.decrementItemCount(product)
                                },
                                itemCount = itemCount
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

//@Preview
//@Composable
//private fun SearchPrev() {
//
//    Search(viewModel())
//}

