package com.example.foodorderapp.view.main.components


import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.foodorderapp.R
import com.example.foodorderapp.data.ProductItemData

@Composable
fun ItemDesign(
    mainProductData: ProductItemData,
    onProductAddClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .height(315.dp)
            .width(240.dp)
            .background(
                shape = RoundedCornerShape(17.dp),
                color = Color.Transparent
            ),
    ) {
        AsyncImage(
            model = mainProductData.productImages,
            contentDescription = "image for the product",
            modifier = Modifier
                .fillMaxWidth()
                .size(140.dp)
        )
        Spacer(modifier= Modifier.height(28.dp))
        Text(
            text = mainProductData.productTitle ,
            fontSize = 22.sp,
            color = Color.White,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 3.dp)
        )
        Text(
            text = "(${mainProductData.productRating})" ,
            fontSize = 14.sp,
            color = Color.Gray,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 3.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 15.dp)
            ,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = "Rs. ${mainProductData.priceInRs}",
                fontSize = 22.sp,
                color = Color.White,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .padding(horizontal = 7.dp, vertical = 3.dp)
                    .weight(1f)
            )

            Button(
                modifier = Modifier
                    .height(60.dp)
                    .wrapContentWidth()
                    .padding(top = 8.dp, end = 15.dp, start = 5.dp, bottom = 8.dp)
                    .border(
                        width = 1.dp,
                        color = Color.White,
                        shape = RoundedCornerShape(18.dp)
                    ),
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.White,
                    containerColor = Color.Transparent
                ),
                onClick = {
                    onProductAddClick()
                }
            ) {
                Text(
                    text = "Add",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White,
                   // modifier = Modifier.padding(0.dp)
                )
            }

        }
    }
}
val mockProduct = ProductItemData(
    productTitle = "Sample Product",
    quantityInKg = "2",
    priceInRs = "200",
    unit = "Kg",
    noOfStocks = "50",
    productCategory = "Electronics",
    productType = "Gadget",
    productImages = listOf(Uri.parse("https://example.com/product_image.jpg")),
    productRating = 4.5f
)
@Preview
@Composable
private fun ItemdesignPreview() {
    ItemDesign(
        mockProduct,
        onProductAddClick = {}
    )
}