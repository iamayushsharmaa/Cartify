package com.example.foodorderapp.view.main.components

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp


@Composable
fun ShimmerItemBox(
    isLoading: Boolean,
    contentAfterLoading: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    if(isLoading){
        Column(
            modifier = Modifier
                .height(315.dp)
                .width(240.dp)
                .clip(shape = RoundedCornerShape(17.dp))
                .background(
                    color = Color.Transparent
                ),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .size(140.dp)
                    .shimmerEffect()
            )
            Spacer(modifier= Modifier.height(28.dp))
            Box(
                modifier = Modifier
                    .height(27.dp)
                    .width(150.dp)
                    .padding(horizontal = 10.dp, vertical = 3.dp)
                    .shimmerEffect()
            )
            Box(
                modifier = Modifier
                    .height(24.dp)
                    .width(50.dp)
                    .padding(horizontal = 10.dp, vertical = 3.dp)
                    .shimmerEffect()
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 15.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Box(
                    modifier = Modifier
                        .height(35.dp)
                        .width(31.dp)
                        .padding(horizontal = 7.dp, vertical = 3.dp)
                        .weight(1f)
                        .shimmerEffect()
                )

                Box(
                    modifier = Modifier
                        .size(70.dp)
                        .weight(1f)
                        .padding(top = 3.dp, end = 8.dp, start = 55.dp, bottom = 9.dp)
                        .shimmerEffect()
                        .background(
                            shape = RoundedCornerShape(18.dp),
                            color = Color.White
                        ),
                )
            }
        }
    }else {
        contentAfterLoading()
    }
}

@SuppressLint("ModifierFactoryUnreferencedReceiver")
fun Modifier.shimmerEffect(): Modifier = composed {
    var size by remember { mutableStateOf(IntSize.Zero) }

    val transition = rememberInfiniteTransition()
    val startOffsetX by transition.animateFloat(
        initialValue = -2 * size.width.toFloat(),
        targetValue = 2 * size.width.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(1000)
        )
    )
    background(
        brush = Brush.linearGradient(
            colors = listOf(
                Color(0xFFE0DFDF),
                Color(0xFFD6D2D2),
                Color(0xFFE0DFDF)
            ),
            start = Offset(startOffsetX,0f),
            end = Offset(startOffsetX + size.width.toFloat() , size.height.toFloat())
        )
    )
        .onGloballyPositioned {
            size = it.size
        }
}
