package com.example.lkwangsit.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

// ------------------ Base Palette ------------------
val White                 = Color(0xFFFFFFFF)
val Black                 = Color(0xFF000000)

val DarkGreen             = Color(0xFF047857) // brand primary
val Green                 = Color(0xFF00A455)
val LightGreen            = Color(0xFFB8FFDC)
val SlightDarkerLightGreen= Color(0xFFECFDF5)

val Red                   = Color(0xFFCC001B)
val LightRed              = Color(0xFFFFCCD3)

val Gray700               = Color(0xFF4B4D4E)
val Gray200               = Color(0xFFE5E6E6)

val LavenderGray          = Color(0xFFB5B3C7)
val DarkLavenderGray      = Color(0xFF6C688D)

val OutlineLight          = Color(0xFFE0E3E7)
val OutlineDark           = Color(0xFF3A3D40)

val SurfaceDark           = Color(0xFF121212)
val SurfaceDarkVariant    = Color(0xFF1A1A1A)
val OnSurfaceDark         = Color(0xFFE6E6E6)
val OnSurfaceVariantDark  = Color(0xFFB7B7B7)

val OnSurfaceLight        = Color(0xFF1B1B1B)
val OnSurfaceVariantLight = Color(0xFF475569)

// ------------------ Extra Colors ------------------
data class ExtraColors(
    val success: Color,
    val onSuccess: Color,
    val error: Color,
    val onError: Color,
    val secondary: Color,
    val onSecondary: Color,
    val neutral: Color,
    val onNeutral: Color,
)

val LocalExtraColors = staticCompositionLocalOf {
    ExtraColors(
        success = LightGreen,
        onSuccess = Green,
        error = LightRed,
        onError = Red,
        secondary = Gray200,
        onSecondary = Gray700,
        neutral = LavenderGray,
        onNeutral = DarkLavenderGray
    )
}

// ------------------ Color Schemes ------------------
val LightColorScheme = lightColorScheme(
    primary = DarkGreen,
    onPrimary = White,

    background = White,
    onBackground = OnSurfaceLight,

    surface = White,
    onSurface = OnSurfaceLight,

    surfaceVariant = White,
    onSurfaceVariant = OnSurfaceVariantLight,

    outline = OutlineLight,

    error = Red,
    onError = White
)

val DarkColorScheme = darkColorScheme(
    primary = DarkGreen,
    onPrimary = White,

    background = SurfaceDark,
    onBackground = OnSurfaceDark,

    surface = SurfaceDark,
    onSurface = OnSurfaceDark,

    surfaceVariant = SurfaceDarkVariant,
    onSurfaceVariant = OnSurfaceVariantDark,

    outline = OutlineDark,

    error = LightRed,
    onError = Red
)
