package com.example.foodorderapp.view.main.bottomnav


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.foodorderapp.R
import com.example.foodorderapp.data.model.Category
import com.example.foodorderapp.ui.theme.poppinsFontFamily
import com.example.foodorderapp.utils.Constants.allProductCategory
import com.example.foodorderapp.utils.Constants.allProductCategoryIcon
import com.example.foodorderapp.view.main.components.CartBottomSheet
import com.example.foodorderapp.view.main.components.CartView
import com.example.foodorderapp.view.main.components.CategoryItemDesign
import com.example.foodorderapp.view.main.components.SearchField
import com.example.foodorderapp.viewmodel.CartViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(
    navController: NavController,
    cartViewModel: CartViewModel = hiltViewModel(),
    onSearchClick: () -> Unit
) {
    var showBottomSheetCart by remember { mutableStateOf(false) }
    val isCartVisible by cartViewModel.isCartVisible.observeAsState(false)
    val itemCount by cartViewModel.itemCount.observeAsState(0)

    val categories = allProductCategory.zip(allProductCategoryIcon) { title, imageResId ->
        Category(title, imageResId)
    }
    var selectedCategory by remember { mutableStateOf("") }

    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ){
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(
                text = "Cartify",
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black,
                fontSize = 28.sp,
            )

            IconButton(
                onClick = {},
                modifier = Modifier
                    .size(46.dp)
                    .padding(end = 8.dp)
            ){
                Icon(
                    painter = painterResource(id = R.drawable.shop_icon),
                    modifier = Modifier
                        .padding(5.dp),
                    tint = Color.Black,
                    contentDescription = "icon for carts"
                )
            }
        }
        SearchField(
            modifier = Modifier,
            onSearchClick = {
                onSearchClick()
            }
        )
        Spacer(Modifier.height(10.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .padding(horizontal = 14.dp, vertical = 8.dp)
                .background(shape = RoundedCornerShape(22.dp), color = Color.Transparent)
        ){
            Image(
                painter = painterResource(id = R.drawable.slider_image),
                contentDescription = "banner image",
                modifier = Modifier
                    .fillMaxSize()
                    .background(shape = RoundedCornerShape(22.dp), color = Color.Transparent)
                    .align(Alignment.Center),
                contentScale = ContentScale.Crop
            )
        }

        Column (modifier = Modifier.padding(5.dp)){
            Text(
                text = "Category",
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 22.sp,
            )
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 2.dp),
                contentPadding = PaddingValues(2.dp)
            ) {
                items(categories) { categoryItem ->
                    CategoryItemDesign(
                        category = categoryItem,
                        onCategoryClick = {
                            selectedCategory = categoryItem.title
                            navController.navigate("categoryScreen/$selectedCategory")
                        }
                    )
                }
            }
        }
        AnimatedVisibility(
            visible = isCartVisible,
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier
                .fillMaxWidth()
        ) {
            CartView(
                itemCount = itemCount,
                onCheckout = {},
                onCartItemClick = {
                    showBottomSheetCart = true
                }
            )
        }
        if (showBottomSheetCart){
            CartBottomSheet(
                onDismiss = { showBottomSheetCart = false }
            )
        }

    }
}
