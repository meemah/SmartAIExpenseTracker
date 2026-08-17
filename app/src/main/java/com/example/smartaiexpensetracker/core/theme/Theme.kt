package com.example.smartaiexpensetracker.core.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

data class CustomThemeColor(
    val primary: Color,
    val background: Color,
    val onSurface: Color,
    val onSurfaceVariant: Color,
    val surface: Color,
    val outlineVariant: Color,
    val onPrimaryContainer: Color,
    val onSecondaryContainer: Color,
    val slate: Color,
    val glassBackground: Color,
    val glassBorder: Color,
    val surfaceContainer: Color
)

private val DarkCustomColors = CustomThemeColor(
    primary = Primary,
    background = DarkBackground,
    onSurface = DarkOnSurface,
    onSurfaceVariant = DarkOnSurfaceVariant,
    surface = DarkSurface,
    outlineVariant = DarkOutlineVariant,
    onPrimaryContainer = DarkOnPrimaryContainer,
    onSecondaryContainer = DarkOnSecondaryContainer,
    slate = DarkSlate,
    glassBackground = DarkGlassBackground,
    glassBorder = DarkGlassBorder,
    surfaceContainer = DarkSurfaceContainer
)

private val LightCustomColors = CustomThemeColor(
    primary = LightPrimary,
    background = LightBackground,
    onSurface = LightOnSurface,
    onSurfaceVariant = LightOnSurfaceVariant,
    surface = LightSurface,
    outlineVariant = LightOutlineVariant,
    onPrimaryContainer = LightOnPrimaryContainer,
    onSecondaryContainer = LightOnSecondaryContainer,
    slate = LightSlate,
    glassBackground = LightGlassBackground,
    glassBorder = LightGlassBorder,
    surfaceContainer = LightSurfaceContainer
)

private val DarkMaterialColors = darkColorScheme(
    primary = Primary,
    background = DarkBackground,
    onSurface = DarkOnSurface,
    onSurfaceVariant = DarkOnSurfaceVariant,
)

private val LightMaterialColors = lightColorScheme(
    primary = LightPrimary,
    background = LightBackground,
    onSurface = LightOnSurface,
    onSurfaceVariant = LightOnSurfaceVariant,
)

val LocalCustomColors = compositionLocalOf { DarkCustomColors }

val MaterialTheme.customColors: CustomThemeColor
    @Composable
    @ReadOnlyComposable
    get() = LocalCustomColors.current


@Composable
fun SmartAIExpenseTrackerTheme(
    darkTheme: Boolean =
        isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkMaterialColors
        else -> LightMaterialColors
    }

    val customColorScheme = if (darkTheme) DarkCustomColors else LightCustomColors

    CompositionLocalProvider(
        LocalCustomColors provides customColorScheme
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = AppTypography,
            content = content
        )
    }
}
