package com.example.speedometer

import android.graphics.Paint
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.inset
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.speedometer.ui.theme.BlueZodiac
import com.example.speedometer.ui.theme.BrightTurquoise
import com.example.speedometer.ui.theme.Malibu
import com.example.speedometer.ui.theme.SpeedometerTheme
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.roundToInt
import kotlin.math.sin

private const val ArcStartAngleDegrees = 125.0f
private const val ArcCompleteSweepAngleDegrees = 290.0f

private val progressBrush = Brush.linearGradient(listOf(Malibu, BrightTurquoise))

private val backgroundArcBrush = Brush.linearGradient(
    listOf(
        Malibu.copy(alpha = 0.1f),
        BrightTurquoise.copy(alpha = 0.1f),
    )
)

private val arcStroke = Stroke(
    width = 40f,
    cap = StrokeCap.Round,
)

@Composable
fun Speedometer(modifier: Modifier, progress: Float, title: String? = null) {
    BoxWithConstraints(modifier = modifier.wrapContentHeight()) {
        SpeedometerArc(
            radius = maxWidth / 2,
            progress = progress,
        )
        Column(
            modifier = Modifier.matchParentSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            if (!title.isNullOrEmpty()) {
                Text(
                    text = title,
                    color = Color.Black,
                    style = MaterialTheme.typography.h2,
                )
            }

            Text(
                text = stringResource(R.string.total_savings),
                color = BlueZodiac,
                style = MaterialTheme.typography.caption,
            )
        }
    }
}

@Composable
private fun SpeedometerArc(radius: Dp, progress: Float) {
    val currentSweepAngle = ArcCompleteSweepAngleDegrees * progress
    val progressText = "${(progress * 100).roundToInt()}%"
    val textSize = with(LocalDensity.current) { 16.sp.toPx() }

    Canvas(
        modifier = Modifier
            .width(radius * 2)
            .height(radius * 2),
    ) {
        inset(arcStroke.width / 2) {
            drawArc(
                brush = backgroundArcBrush,
                startAngle = ArcStartAngleDegrees,
                sweepAngle = ArcCompleteSweepAngleDegrees,
                useCenter = false,
                style = arcStroke,
            )
            drawArc(
                brush = progressBrush,
                startAngle = ArcStartAngleDegrees,
                sweepAngle = currentSweepAngle,
                useCenter = false,
                style = arcStroke,
            )
            drawIntoCanvas {
                val textPosition = calculateArcEnd(radius, arcStroke, currentSweepAngle)
                it.nativeCanvas.drawText(
                    progressText,
                    textPosition.x,
                    textPosition.y,
                    generateTextPaint(textSize),
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SpeedometerPreview() {
    SpeedometerTheme {
        Speedometer(
            progress = 0.35f,
            modifier = Modifier
                .padding(5.dp)
                .width(220.dp),
            title = "2.000 €",
        )
    }
}

private fun calculateExtraDegreesForText(x: Float): Float {
    // Cubic regression
    val y = 8.909492 - 3.354332 * x + 26.65545 * x.pow(2) - 17.34427 * x.pow(3)
    return y.toFloat()
}

private fun DrawScope.calculateArcEnd(
    radius: Dp,
    arcStroke: Stroke,
    sweepAngleDegrees: Float,
): Offset {
    val progress = sweepAngleDegrees / ArcCompleteSweepAngleDegrees
    val extraDegrees = calculateExtraDegreesForText(progress)
    val radiusFix = radius.toPx() - arcStroke.width / 1.5f
    val endAngle = ArcStartAngleDegrees + sweepAngleDegrees + extraDegrees
    val x = cos(endAngle.rad) * radiusFix + center.x
    val y = sin(endAngle.rad) * radiusFix + center.y
    return Offset(x, y)
}

private fun generateTextPaint(pxTextSize: Float) = Paint().apply {
    textAlign = Paint.Align.CENTER
    textSize = pxTextSize
    color = android.graphics.Color.rgb(84, 171, 253)
}