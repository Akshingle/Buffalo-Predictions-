package com.buffalomilkpredictor.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// Primary Color - Orange (indicating Buffalo/Agriculture)
private val PrimaryColor = Color(0xFFFF9800)
private val PrimaryColorLight = Color(0xFFFFB74D)
private val PrimaryColorDark = Color(0xFFF57C00)

// Secondary Color - Green (Dairy/Agriculture)
private val SecondaryColor = Color(0xFF4CAF50)
private val SecondaryColorLight = Color(0xFF81C784)
private val SecondaryColorDark = Color(0xFF388E3C)

// Tertiary Color - Brown (Earth tone)
private val TertiaryColor = Color(0xFF795548)

// Background and Surface
private val LightBackgroundColor = Color(0xFFFAFAFA)
private val DarkBackgroundColor = Color(0xFF121212)
private val LightSurfaceColor = Color(0xFFFFFFFF)
private val DarkSurfaceColor = Color(0xFF1E1E1E)

private val LightColorScheme = lightColorScheme(
    primary = PrimaryColor,
    onPrimary = Color.White,
    primaryContainer = PrimaryColorLight,
    onPrimaryContainer = Color(0xFF4D2600),
    secondary = SecondaryColor,
    onSecondary = Color.White,
    secondaryContainer = SecondaryColorLight,
    onSecondaryContainer = Color(0xFF1B5E20),
    tertiary = TertiaryColor,
    onTertiary = Color.White,
    tertiaryContainer = Color(0xFFD7CCC8),
    onTertiaryContainer = Color(0xFF3E2723),
    error = Color(0xFFB3261E),
    onError = Color.White,
    errorContainer = Color(0xFFF9DEDC),
    onErrorContainer = Color(0xFF410E0B),
    background = LightBackgroundColor,
    onBackground = Color(0xFF1C1B1F),
    surface = LightSurfaceColor,
    onSurface = Color(0xFF1C1B1F),
    surfaceVariant = Color(0xFFE7E0EC),
    onSurfaceVariant = Color(0xFF49454E),
    outline = Color(0xFF79747E),
    outlineVariant = Color(0xFFCAC7D0),
    scrim = Color.Black
)

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryColorLight,
    onPrimary = Color(0xFF662D00),
    primaryContainer = PrimaryColorDark,
    onPrimaryContainer = Color(0xFFFFB74D),
    secondary = SecondaryColorLight,
    onSecondary = Color(0xFF003300),
    secondaryContainer = SecondaryColorDark,
    onSecondaryContainer = Color(0xFF81C784),
    tertiary = Color(0xFFD7CCC8),
    onTertiary = Color(0xFF442723),
    tertiaryContainer = Color(0xFF5D4037),
    onTertiaryContainer = Color(0xFFFFDBCF),
    error = Color(0xFFF2B8B5),
    onError = Color(0xFF601410),
    errorContainer = Color(0xFF8C1D18),
    onErrorContainer = Color(0xFFF2B8B5),
    background = DarkBackgroundColor,
    onBackground = Color(0xFFE6E1E6),
    surface = DarkSurfaceColor,
    onSurface = Color(0xFFE6E1E6),
    surfaceVariant = Color(0xFF49454E),
    onSurfaceVariant = Color(0xFFCAC7D0),
    outline = Color(0xFF94919B),
    outlineVariant = Color(0xFF49454E),
    scrim = Color.Black
)

@Composable
fun BuffaloMilkPredictorTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view)?.isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = BuffaloTypography,
        content = content
    )
}
