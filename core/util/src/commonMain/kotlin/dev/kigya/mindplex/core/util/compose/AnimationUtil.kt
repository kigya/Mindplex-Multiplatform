package dev.kigya.mindplex.core.util.compose

import androidx.compose.animation.ContentTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith

fun fadeSlideScaleContentTransitionSpec(): ContentTransform =
    fadeIn(animationSpec = tween()) + slideInVertically(
        animationSpec = tween(),
        initialOffsetY = { it / 2 },
    ) + scaleIn(
        initialScale = 0.8f,
        animationSpec = tween(),
    ) togetherWith fadeOut(animationSpec = tween()) + slideOutVertically(
        animationSpec = tween(),
        targetOffsetY = { -it / 2 },
    ) + scaleOut(
        targetScale = 0.8f,
        animationSpec = tween(),
    )
