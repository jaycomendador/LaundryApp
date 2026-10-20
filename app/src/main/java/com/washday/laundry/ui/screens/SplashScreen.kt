package com.washday.laundry.ui.screens

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocalLaundryService
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.washday.laundry.ui.theme.LaundryPinkPrimary
import com.washday.laundry.ui.theme.LaundryPinkSoftBg
import com.washday.laundry.ui.theme.LaundryTextPrimary
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun SplashScreen(
    onSplashFinished: () -> Unit
) {
    LaunchedEffect(Unit) {
        delay(2200.milliseconds)
        onSplashFinished()
    }

    val infiniteTransition = rememberInfiniteTransition(label = "splash")

    // Pulsing Scale Animation
    val scaleAnim by infiniteTransition.animateFloat(
        initialValue = 0.95f,
        targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )

    // Gentle Rotation Animation
    val rotationAnim by infiniteTransition.animateFloat(
        initialValue = -8f,
        targetValue = 8f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "rotation"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(LaundryPinkSoftBg),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(32.dp)
        ) {
            // Animated Washing Machine + Heart Logo
            Box(
                modifier = Modifier
                    .scale(scaleAnim)
                    .rotate(rotationAnim)
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(LaundryPinkPrimary),
                contentAlignment = Alignment.Center
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Default.LocalLaundryService,
                        contentDescription = "Laundry Mate Logo",
                        tint = Color.White,
                        modifier = Modifier.size(56.dp)
                    )
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Laundry Mate",
                fontSize = 32.sp,
                fontWeight = FontWeight.ExtraBold,
                color = LaundryPinkPrimary,
                letterSpacing = (-0.5).sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Clean Clothes Happy Days",
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                color = LaundryTextPrimary
            )

            Spacer(modifier = Modifier.height(48.dp))

            CircularProgressIndicator(
                color = LaundryPinkPrimary,
                strokeWidth = 3.dp,
                modifier = Modifier.size(36.dp)
            )
        }
    }
}
