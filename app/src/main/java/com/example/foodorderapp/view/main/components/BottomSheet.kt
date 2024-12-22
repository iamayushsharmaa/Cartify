package com.example.foodorderapp.view.main.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.example.foodorderapp.R
import com.example.foodorderapp.data.model.CartProduct
import com.example.foodorderapp.utils.toProductItemData
import com.example.foodorderapp.viewmodel.CartViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartBottomSheet(
    onDismiss: () -> Unit,
    cartViewModel: CartViewModel = hiltViewModel()
) {
    val cartProduct by cartViewModel.cartProducts.collectAsState(emptyList())

    ModalBottomSheet(
        onDismissRequest = { onDismiss() }
    ) {
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(16.dp),
            horizontalAlignment = Alignment.Start
        ){
            Text(
                text = "Cart Products",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(12.dp)
            )

            Spacer(Modifier.height(8.dp))

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)
            ) {
                items(cartProduct){ cartProduct->
                    val productItemData = cartProduct.toProductItemData()
                    BottomSheetCartDesign(
                        cartProduct = cartProduct,
                        onIncrementClick = {cartViewModel.incrementItemCount(productItemData)},
                        onDecrementClick = {cartViewModel.decrementItemCount(productItemData)},
                    )
                }

            }
        }
    }
}

@Composable
fun BottomSheetCartDesign(
    cartProduct: CartProduct,
    onIncrementClick : () -> Unit,
    onDecrementClick : () -> Unit,
) {
    Row (
        modifier = Modifier
            .fillMaxWidth()

    ){
        AsyncImage(
            model = cartProduct.productImages,
            contentDescription = "product cart in bottom sheet",
            modifier = Modifier
                .height(100.dp)
                .weight(1f)
        )
        Column (
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 15.dp)
        ){
            Text(
                text = cartProduct.productTitle,
                fontWeight = FontWeight.SemiBold,
                fontSize = 24.sp,
                modifier = Modifier
                    .fillMaxWidth()
            )

            Text(
                text = "${cartProduct.quantityInKg}",
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 5.dp)
            )
        }

        Spacer(Modifier.width(16.dp))
        Row(
            modifier = Modifier
                .height(30.dp)
                .width(60.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            IconButton(onClick = { onDecrementClick() }) {
                Icon(
                    painter = painterResource(id = R.drawable.minus_svg), // Replace with actual icon resource
                    contentDescription = "Remove Item",
                    tint = Color.White
                )
            }
            Text(
                text = "${cartProduct.productCount}",
                fontSize = 12.sp,
                color = Color.White,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            IconButton(onClick = { onIncrementClick()}) {
                Icon(
                    painter = painterResource(id = R.drawable.add_svg), // Replace with actual icon resource
                    contentDescription = "Add Item",
                    tint = Color.White
                )
            }
        }
    }
}
