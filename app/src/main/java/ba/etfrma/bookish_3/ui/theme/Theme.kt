// File: app/src/main/java/ba/etfrma/bookish_3/ui/theme/Theme.kt
package ba.etfrma.bookish_3.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary = Color(0xFF764C4C),
    onPrimary = OnPrimaryColor,
    primaryContainer = PrimaryVariantColor,
    secondary = SecondaryColor,
    onSecondary = OnSecondaryColor,
    background = Color(0xFFA85050),
    onBackground = OnBackgroundColor,
    surface = SurfaceColor,
    onSurface = OnSurfaceColor,
    error = ErrorColor,
    onError = OnErrorColor
)

private val DarkColors = darkColorScheme(
    primary = Color(0xFFCDC5C5),
    onPrimary = PrimaryColorDark,
    primaryContainer = PrimaryVariantColor,
    secondary = SecondaryColor,
    onSecondary = OnSecondaryColor,
    background = Color(0xFF6D5C5C),
    onBackground = OnBackgroundColor,
    surface = Color.Black,
    onSurface = Color.White,
    error = ErrorColor,
    onError = OnErrorColor
)

@Composable
fun Bookish_3Theme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColors
    } else {
        LightColors
    }

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        content = content
    )
}