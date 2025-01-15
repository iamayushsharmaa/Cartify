package com.example.foodorderapp.view.main.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.foodorderapp.R
import com.example.foodorderapp.data.model.Category
import com.example.foodorderapp.ui.theme.poppinsFontFamily

@Composable
fun CategoryItemDesign(
    category : Category,
    onCategoryClick: () -> Unit
) {
    Column (
        modifier = Modifier
            .size(74.dp)
            .background(color = Color.Transparent),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Box(
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape),
            contentAlignment = Alignment.Center
        ){
            Image(
                painter = painterResource(id = category.image),
                contentDescription = "image for the product",
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color(0xFFD4D4D4))
                    .padding(5.dp)

            )
        }
        Text(
            text = "Grocery",
            fontSize = 11.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(2.dp),
            fontFamily = poppinsFontFamily,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Center,
            color = Color.Black,
            maxLines = 1,

        )

    }
}

//@Preview
//@Composable
//private fun Item() {
//    CategoryItemDesign(
//        category = Category(
//            title = "Dairy Products",
//            image = R.drawable.png_milk),
//        onCategoryClick = {}
//    )
//
//}