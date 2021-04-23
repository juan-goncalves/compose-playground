package com.example.speedometer

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.speedometer.ui.theme.SpeedometerTheme
import kotlin.math.PI

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SpeedometerTheme {
                if (true) {
                    SavingsIntroScreen()
                } else {
                    SavingsScreen()
                }
            }
        }
    }
}

@Composable
fun SavingsScreen() {
    Surface(color = MaterialTheme.colors.background) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(top = 10.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AnimatingSpeedometer(
                modifier = Modifier.width(300.dp),
                progress = 0.35f,
                title = "2.000 €",
            )
        }
    }
}

@Composable
fun SavingsIntroScreen() {
    Surface(color = MaterialTheme.colors.background) {
        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Speedometer(
                    modifier = Modifier.width(300.dp),
                    progress = 0.35f,
                )
            }
            Overlay(Color.White)
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.BottomCenter,
            ) {
                Column {
                    Image(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(horizontal = 32.dp),
                        painter = painterResource(R.drawable.illustration),
                        contentDescription = null,
                    )
                    SavingsIntroActions(modifier = Modifier.padding(16.dp))
                    BottomNavBarPlaceholder()
                }
            }
        }
    }
}

@Composable
fun Overlay(color: Color) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color.copy(alpha = 0.6f)),
    )
}

@Composable
fun BottomNavBarPlaceholder() {
    Box(
        modifier = Modifier
            .height(50.dp)
            .background(Color.LightGray.copy(alpha = 0.3f))
            .fillMaxWidth(),
    )
}

@Composable
fun SavingsIntroActions(
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Savings",
            style = MaterialTheme.typography.h5,
            fontWeight = FontWeight.SemiBold,
        )
        Text(
            modifier = Modifier.padding(top = 16.dp, start = 8.dp, end = 8.dp),
            text = "Add a saving account and become even better at managing your finances!",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.body1,
        )
        Button(
            elevation = null,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp),
            shape = MaterialTheme.shapes.small.copy(CornerSize(percent = 50)),
            onClick = {
                context.startActivity(Intent(context, EmptyActivity::class.java))
            },
        ) {
            Text(
                modifier = Modifier.padding(vertical = 5.dp),
                text = "Connect a savings account",
            )
        }
    }
}

@Preview
@Composable
fun SavingsIntroScreenPreview() {
    SpeedometerTheme {
        SavingsIntroScreen()
    }
}

val Float.rad: Float get() = this * PI.toFloat() / 180f
