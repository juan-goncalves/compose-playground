package com.example.speedometer.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.speedometer.R

val azoSansFontFamily = FontFamily(
    Font(R.font.azo_sans_light, FontWeight.Light),
    Font(R.font.azo_sans_regular, FontWeight.Normal),
    Font(R.font.azo_sans_medium, FontWeight.Medium),
)

val Typography = Typography(
    defaultFontFamily = azoSansFontFamily,
    h2 = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 30.sp
    ),
    body1 = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    caption = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
)
