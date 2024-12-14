package com.example.foodorderapp.view.main.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.foodorderapp.data.Category


@Composable
fun CategoryCard(
    modifier: Modifier = Modifier,
    category: Category,
    onCategoryClick: (Category) -> Unit
) {
    Column (
        modifier = Modifier
            .width(98.dp)
            .clickable {
                onCategoryClick(category)
            }
            .wrapContentHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center

    ){
        Box(
            modifier = Modifier
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(17.dp)
                ) // Add rounded corners
                .padding(8.dp)
                .clip(RoundedCornerShape(17.dp)),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(category.image),
                contentDescription = "image for the product",
                modifier = Modifier
                    .size(60.dp)
                    .padding(5.dp)
            )
        }
        Text(
            text = category.title,
            modifier = Modifier
                .fillMaxWidth()
                .padding(3.dp),
            textAlign = TextAlign.Center,
            color = Color.White,
            fontWeight = FontWeight.SemiBold,
            fontSize = 11.sp
        )
    }
}

@Preview
@Composable
private fun Pre() {
    CategoryCard(
        category = Category(title = "Milk", image = R.drawable.png_milk),
        onCategoryClick = {})
}
