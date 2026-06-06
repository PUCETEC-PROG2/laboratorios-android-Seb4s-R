package ec.edu.puce.githubclient.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

// =======================================================
// TEMA OSCURO
// =======================================================
//
// Este será el tema principal de la aplicación.
//
// La combinación:
//
// DeepOceanBlue
// ElectricBlue
// BrightTurquoise
//
// produce una apariencia moderna tipo dashboard,
// muy cercana al estilo Apple y VisionOS.
// =======================================================

private val DarkColorScheme = darkColorScheme(

    // Color principal
    primary = ElectricBlue,

    // Color secundario
    secondary = BrightTurquoise,

    // Color terciario
    tertiary = SoftTurquoise,

    // Fondo principal
    background = DeepOceanBlue,

    // Comentario: Se cambia a un color sólido para evitar superposición ilegible en diálogos
    surface = Color(0xFF0F2540),

    // Texto sobre primary
    onPrimary = Color.White,

    // Texto sobre secondary
    onSecondary = TextPrimary,

    // Texto sobre fondo
    onBackground = TextPrimary,

    // Texto sobre superficies
    onSurface = TextPrimary
)


// =======================================================
// TEMA CLARO
// =======================================================
//
// Mantiene coherencia visual con la misma paleta.
// =======================================================

private val LightColorScheme = lightColorScheme(

    primary = ElectricBlue,

    secondary = BrightTurquoise,

    tertiary = PuceBlue,

    background = SoftTurquoise,

    // Comentario: Se cambia a sólido en el tema claro también
    surface = Color.White,

    onPrimary = TextPrimary,

    onSecondary = TextPrimary,

    onBackground = DeepOceanBlue,

    onSurface = DeepOceanBlue
)


// =======================================================
// TEMA GLOBAL DE LA APLICACIÓN
// =======================================================
//
// Este composable envuelve toda la app.
//
// Se encarga de:
//
// - Aplicar colores
// - Aplicar tipografía
// - Aplicar Material3
// =======================================================

@Composable
fun GithubClientTheme(

    darkTheme: Boolean = isSystemInDarkTheme(),

    dynamicColor: Boolean = false,

    content: @Composable () -> Unit

) {

    // ===================================================
    // SELECCIÓN DEL ESQUEMA DE COLORES
    // ===================================================

    val colorScheme = when {

        // Android 12+
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {

            val context = LocalContext.current

            if (darkTheme)
                dynamicDarkColorScheme(context)
            else
                dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme

        else -> LightColorScheme
    }

    // ===================================================
    // MATERIAL THEME
    // ===================================================
    //
    // Aplica:
    //
    // - Colores
    // - Tipografía
    // - Componentes Material3
    //
    MaterialTheme(

        colorScheme = colorScheme,

        typography = Typography,

        content = content
    )
}