package com.example.foodorderapp.view.main.components



import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import coil3.compose.AsyncImagePainter.State.Empty.painter
import com.example.foodorderapp.R


@Composable
fun CartView(
    itemCount: Int,
    onCheckout : () -> Unit
) {
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(color = Color.Black)
    ){
        Card(
            modifier = Modifier
                .height(80.dp)
                .width(195.dp)
                .padding(12.dp),
            colors = CardDefaults.cardColors(
                contentColor = Color.White,
                containerColor = Color(0xFF434343)
            ),
            shape = RoundedCornerShape(12.dp)
        ){
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(6.dp)
            ){
                Icon(
                    modifier = Modifier
                        .size(36.dp)
                        .padding(start = 2.dp),
                    painter = painterResource(R.drawable.shopping_cart_yellow),
                    contentDescription = "shop icon for cart"
                )

                Text(
                    text = "$itemCount item",
                    fontSize = 18.sp,

                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        //.width(50.dp)
                        .padding(start = 10.dp, top = 11.dp)
                )
                Spacer(Modifier.width(26.dp))
                IconButton(
                    onClick = {},
                    modifier = Modifier
                        .size(32.dp)
                        .padding(top = 8.dp, end = 2.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.caret_arrow_up),
                        contentDescription = "upDownIcon"
                    )
                }
            }
        }
        Button(
            modifier = Modifier
                .height(80.dp)
                .weight(1f)
                .padding(top = 12.dp, end = 12.dp, bottom = 12.dp),
            onClick = {
                onCheckout()
            },
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.White,
                containerColor = Color(0xFF434343)
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Checkout",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Icon(
                    modifier = Modifier
                        .size(40.dp)
                        .padding(8.dp),
                    painter = painterResource(R.drawable.next_double_arrow_svg),
                    contentDescription = "Next Cart Button"
                )
            }
        }

    }

}

@Preview
@Composable
private fun CartPrev() {
    CartView(
        itemCount= 1,
        onCheckout = {}
    )

}