package com.example.ui.theme

import android.os.Build
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val BlackEmeraldColorScheme = darkColorScheme(
    primary = RadiantGreenPrimary,
    onPrimary = RadiantGreenOnPrimary,
    primaryContainer = RadiantGreenContainer,
    onPrimaryContainer = RadiantGreenOnContainer,
    secondary = EmeraldSecondary,
    onSecondary = EmeraldOnSecondary,
    secondaryContainer = EmeraldSecondaryContainer,
    onSecondaryContainer = EmeraldOnSecondaryContainer,
    tertiary = MintGreenLight,
    onTertiary = Color(0xFF003820),
    tertiaryContainer = DeepForestGreen,
    onTertiaryContainer = MintGreenLight,
    background = DeepBlackBackground,
    onBackground = TextWhitePrimary,
    surface = DeepBlackBackground,
    onSurface = TextWhitePrimary,
    surfaceVariant = CardBlackSurface,
    onSurfaceVariant = TextMutedGreen,
    outline = SurfaceBorderGreen,
    outlineVariant = SurfaceBorderGreen
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = true,
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit,
) {
    // The user requested black with deep green aesthetic specifically
    MaterialTheme(
        colorScheme = BlackEmeraldColorScheme,
        typography = Typography,
        content = content
    )
}
