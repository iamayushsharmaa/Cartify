package com.example.foodorderapp.view.main

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.foodorderapp.R
import com.example.foodorderapp.data.Category
import com.example.foodorderapp.data.DbResult
import com.example.foodorderapp.view.main.components.CategoryCard
import com.example.foodorderapp.view.main.components.ItemDesign
import com.example.foodorderapp.view.main.components.ShimmerItemBox
import com.example.foodorderapp.viewmodel.FirestoreViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryScreen(
    navController: NavController,
    firestoreViewModel: FirestoreViewModel = viewModel(),
    category: String
) {

    val categoryProductState by firestoreViewModel.categoryProductState.collectAsState()


    LaunchedEffect (category){
        firestoreViewModel.getCategoryProduct(category)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(
                        modifier = Modifier.size(50.dp),
                        onClick = {
                            navController.popBackStack()
                        },
                    ) {
                        Icon(
                            painter = if(isSystemInDarkTheme())
                                painterResource(R.drawable.prev_arrow_white)
                            else
                                painterResource(R.drawable.prev_arrow_black)
                            ,
                            contentDescription = "icon for navigation back to home screen"
                        )
                    }
                },
                title = {
                    Text(
                        text = "Snacks",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(5.dp)
                    )
                },
                actions = {
                    IconButton(
                        onClick = {
                            navController.navigate("searchScreen")
                        }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.icon_search_white),
                            contentDescription = "search icon"
                        )
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            when (categoryProductState) {
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
                   // val filteredProducts by firestoreViewModel.filteredCategoryProducts.collectAsState()

                    val categoryProducts = (categoryProductState as DbResult.Success).data

                    if (categoryProducts.isNullOrEmpty()) {
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
                            items(categoryProducts){ product->
                                ItemDesign(
                                    product,
                                    onProductAddClick = {
                                        // isDialogVisible = true
                                    }
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
                    val errorMessage = (categoryProductState as DbResult.Error).message
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