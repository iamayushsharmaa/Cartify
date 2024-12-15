package com.example.foodorderapp.view.main.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.foodorderapp.R
import com.example.foodorderapp.data.Category

@Composable
fun CategoryItemDesign(
    category : Category,
    onCategoryClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .height(150.dp)
            .width(125.dp)
            .padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFD7D7D7),),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        )
    )  {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(17.dp)
                )
                .clickable {
                    onCategoryClick()
                },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = category.image),
                contentDescription = "image for the product",
                modifier = Modifier.size(80.dp)
            )
            Spacer(modifier= Modifier.height(8.dp))
            Text(
                text = category.title ,
                fontSize = 15.sp,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center,
                color = Color.Black,
                modifier = Modifier.padding(5.dp)
            )
        }
    }
}

@Preview
@Composable
private fun Item() {
    CategoryItemDesign(
        category = Category(
            title = "Dairy Products",
            image = R.drawable.png_milk),
        onCategoryClick = {}
    )

}