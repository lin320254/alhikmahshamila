package com.example.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.R

// Arabic classical font: Amiri
val AmiriFontFamily = FontFamily(
    Font(R.font.amiri, FontWeight.Normal),
    Font(R.font.amiri_bold, FontWeight.Bold)
)

// Urdu Nastaleeq font: Jameel Noori Nastaleeq
val JameelNooriFontFamily = FontFamily(
    Font(R.font.jameel_noori_nastaleeq, FontWeight.Normal)
)

val Typography = Typography(
    displayLarge = TextStyle(
        fontFamily = AmiriFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        lineHeight = 44.sp
    ),
    titleLarge = TextStyle(
        fontFamily = AmiriFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 21.sp,
        lineHeight = 32.sp
    ),
    titleMedium = TextStyle(
        fontFamily = AmiriFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        lineHeight = 28.sp
    ),
    titleSmall = TextStyle(
        fontFamily = AmiriFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 15.sp,
        lineHeight = 24.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = JameelNooriFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        lineHeight = 30.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = JameelNooriFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 28.sp
    ),
    bodySmall = TextStyle(
        fontFamily = JameelNooriFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 24.sp
    ),
    labelLarge = TextStyle(
        fontFamily = AmiriFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 15.sp,
        lineHeight = 22.sp
    ),
    labelMedium = TextStyle(
        fontFamily = AmiriFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 13.sp,
        lineHeight = 20.sp
    ),
    labelSmall = TextStyle(
        fontFamily = AmiriFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp
    )
)
