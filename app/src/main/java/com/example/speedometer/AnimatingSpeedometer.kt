package com.example.speedometer

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.speedometer.ui.theme.SpeedometerTheme


@Composable
fun AnimatingSpeedometer(modifier: Modifier, progress: Float, title: String) {
    // Start animating from 0% or the value before configuration changes
    var initialProgress by rememberSaveable { mutableStateOf(0.0f) }

    val animatedProgress = remember { Animatable(initialProgress) }

    LaunchedEffect("speedometer_enter_anim") {
        animatedProgress.animateTo(
            targetValue = progress,
            animationSpec = tween(durationMillis = 5000, easing = FastOutSlowInEasing),
        )
    }

    initialProgress = animatedProgress.value

    Speedometer(
        title = title,
        modifier = modifier,
        progress = animatedProgress.value,
    )
}

@Preview(showBackground = true)
@Composable
private fun AnimatingSpeedometerPreview() {
    SpeedometerTheme {
        AnimatingSpeedometer(
            progress = 0.35f,
            modifier = Modifier
                .padding(5.dp)
                .width(220.dp),
            title = "2.320 €",
        )
    }
}
