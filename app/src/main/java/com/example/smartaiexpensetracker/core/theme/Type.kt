package com.example.smartaiexpensetracker.core.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp


val DefaultTypography = Typography()
val DefaultFont: FontFamily = ManropeFontFamily;
val HeaderFont: FontFamily = InterFontFamily
val AppTypography = Typography(
    displayLarge = DefaultTypography.displayLarge.copy(
        fontFamily = HeaderFont,
        letterSpacing = (-0.5).sp
    ),
    displayMedium = DefaultTypography.displayMedium.copy(
        fontFamily = HeaderFont,
        letterSpacing = (-0.5).sp
    ),
    displaySmall = DefaultTypography.displaySmall.copy(
        fontFamily = HeaderFont,
        letterSpacing = (-0.25).sp
    ),

    headlineLarge = DefaultTypography.headlineLarge.copy(
        fontFamily = HeaderFont,
        letterSpacing = (-0.25).sp
    ),
    headlineMedium = DefaultTypography.headlineMedium.copy(
        fontFamily = HeaderFont,
        letterSpacing = (-0.25).sp
    ),
    headlineSmall = DefaultTypography.headlineSmall.copy(
        fontFamily = HeaderFont,
        letterSpacing = (-0.15).sp
    ),

    titleLarge = DefaultTypography.titleLarge.copy(
        fontFamily = HeaderFont,
        letterSpacing = 0.sp
    ),
    titleMedium = DefaultTypography.titleMedium.copy(
        fontFamily = HeaderFont,
        letterSpacing = 0.sp
    ),
    titleSmall = DefaultTypography.titleSmall.copy(
        fontFamily = HeaderFont,
        letterSpacing = 0.sp
    ),

    bodyLarge = DefaultTypography.bodyLarge.copy(
        fontFamily = DefaultFont,
        letterSpacing = 0.sp
    ),
    bodyMedium = DefaultTypography.bodyMedium.copy(
        fontFamily = DefaultFont,
        letterSpacing = 0.1.sp
    ),
    bodySmall = DefaultTypography.bodySmall.copy(
        fontFamily = DefaultFont,
        letterSpacing = 0.1.sp
    ),

    labelLarge = DefaultTypography.labelLarge.copy(
        fontFamily = DefaultFont,
        letterSpacing = 0.1.sp
    ),
    labelMedium = DefaultTypography.labelMedium.copy(
        fontFamily = DefaultFont,
        letterSpacing = 0.2.sp
    ),
    labelSmall = DefaultTypography.labelSmall.copy(
        fontFamily = DefaultFont,
        letterSpacing = 0.2.sp
    ),
)
