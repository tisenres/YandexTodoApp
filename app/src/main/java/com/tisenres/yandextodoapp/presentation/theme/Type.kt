package com.tisenres.yandextodoapp.presentation.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.googlefonts.GoogleFont
import com.tisenres.yandextodoapp.R

val robotoFamily = FontFamily(
    Font(resId = R.font.roboto_regular, weight = FontWeight.Normal),
    Font(resId = R.font.roboto_bold, weight = FontWeight.Bold),
    Font(resId = R.font.roboto_black, weight = FontWeight.Black),
    Font(resId = R.font.roboto_light, weight = FontWeight.Light),
    Font(resId = R.font.roboto_medium, weight = FontWeight.Medium)
)

val Typography = Typography(
    titleLarge = TextStyle(
        fontFamily = robotoFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        lineHeight = 38.sp
    ),
    titleMedium = TextStyle(
        fontFamily = robotoFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        lineHeight = 32.sp
    ),
    labelSmall = TextStyle(
        fontFamily = robotoFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        lineHeight = 24.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = robotoFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 20.sp
    ),
    bodySmall = TextStyle(
        fontFamily = robotoFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp
    )
)