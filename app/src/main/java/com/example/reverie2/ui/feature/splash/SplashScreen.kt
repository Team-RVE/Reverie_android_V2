package com.example.reverie2.ui.feature.splash

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.reverie2.R
import kotlinx.coroutines.launch

private val SplashBackground = Color(0xFFFAF9F6)

private const val SplashDelay = 2000L
private const val AlphaDuration = 700
private const val ScaleDuration = 850
private const val RevealDuration = 900

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
) {
    val logoAlpha = remember { Animatable(0f) }
    val logoScale = remember { Animatable(0.85f) }
    val revealProgress = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        launch {
            revealProgress.animateTo(
                targetValue = 1f,
                animationSpec = tween(
                    durationMillis = RevealDuration,
                    easing = LinearEasing,
                ),
            )
        }

        launch {
            logoAlpha.animateTo(
                targetValue = 1f,
                animationSpec = tween(
                    durationMillis = AlphaDuration,
                    easing = LinearEasing,
                ),
            )
        }

        launch {
            logoScale.animateTo(
                targetValue = 1f,
                animationSpec = tween(
                    durationMillis = ScaleDuration,
                    easing = FastOutSlowInEasing,
                ),
            )
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(SplashBackground),
        contentAlignment = Alignment.Center,
    ) {
        Box(
            modifier = Modifier
                .size(140.dp)
                .clipToBounds(),
            contentAlignment = Alignment.Center,
        ) {
            Image(
                painter = painterResource(id = R.drawable.reverie_logo),
                contentDescription = "Reverie Logo",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(400.dp)
                    .graphicsLayer {
                        val progress = revealProgress.value
                        clip = true
                        scaleX = logoScale.value
                        scaleY = logoScale.value
                        alpha = logoAlpha.value
                        translationY = (1f - progress) * 24f
                    },
            )
        }
    }
}