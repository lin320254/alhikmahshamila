package com.example.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

// Apple iOS Liquid Glass Colors
val GlassBackgroundDeep = Color(0xFF040806)
val GlassSurfaceFill = Color(0x2412281D)
val GlassSurfaceFillElevated = Color(0x38183526)
val GlassHighlightTop = Color(0x40FFFFFF)
val GlassHighlightBorder = Color(0x554ADE80)
val GlassBorderSubtle = Color(0x3334D399)
val GlassPillInactive = Color(0x241E3A2B)
val GlassPillActive = Color(0x4822C55E)

/**
 * Ambient background with subtle glowing orbs creating rich refraction for transparent glass
 */
@Composable
fun GlassScaffoldBackground(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(GlassBackgroundDeep)
    ) {
        // Top-right emerald ambient orb
        Box(
            modifier = Modifier
                .size(320.dp)
                .offset(x = 100.dp, y = (-60).dp)
                .background(
                    Brush.radialGradient(
                        colors = listOf(Color(0x3522C55E), Color(0x1810B981), Color.Transparent)
                    ),
                    shape = CircleShape
                )
        )

        // Middle-left teal ambient orb
        Box(
            modifier = Modifier
                .size(280.dp)
                .offset(x = (-100).dp, y = 260.dp)
                .background(
                    Brush.radialGradient(
                        colors = listOf(Color(0x28059669), Color(0x12047857), Color.Transparent)
                    ),
                    shape = CircleShape
                )
        )

        // Bottom-center deep forest orb
        Box(
            modifier = Modifier
                .size(340.dp)
                .align(Alignment.BottomCenter)
                .offset(y = 100.dp)
                .background(
                    Brush.radialGradient(
                        colors = listOf(Color(0x3015803D), Color(0x14052E16), Color.Transparent)
                    ),
                    shape = CircleShape
                )
        )

        content()
    }
}

/**
 * iOS-style Translucent Glass Container with specular reflections
 */
@Composable
fun GlassBox(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(20.dp),
    backgroundColor: Color = GlassSurfaceFill,
    borderColor: Color = GlassBorderSubtle,
    borderWidth: Dp = 1.dp,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
            .clip(shape)
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        backgroundColor.copy(alpha = 0.32f),
                        backgroundColor.copy(alpha = 0.20f),
                        backgroundColor.copy(alpha = 0.26f)
                    )
                )
            )
            .border(
                BorderStroke(
                    borderWidth,
                    Brush.linearGradient(
                        listOf(
                            Color(0x60FFFFFF),
                            borderColor,
                            Color(0x20FFFFFF),
                            borderColor.copy(alpha = 0.4f)
                        )
                    )
                ),
                shape = shape
            )
    ) {
        content()
    }
}
