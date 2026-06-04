package ec.edu.puce.githubclient.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// =======================================================
// TIPOGRAFÍA GLOBAL
// =======================================================
//
// Objetivo:
//
// - Más moderna
// - Más limpia
// - Inspirada en Apple
// - Mejor jerarquía visual
//
// Utilizamos SansSerif para mantener compatibilidad
// total sin instalar fuentes externas.
// =======================================================

val Typography = Typography(

    // ===================================================
    // TITULO PRINCIPAL
    // ===================================================
    //
    // Utilizado para títulos importantes.
    //
    titleLarge = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp
    ),

    // ===================================================
    // TITULO SECUNDARIO
    // ===================================================
    //
    // Ideal para nombres de repositorios.
    //
    titleMedium = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp
    ),

    // ===================================================
    // SUBTÍTULOS
    // ===================================================
    //
    // Utilizado en encabezados secundarios.
    //
    headlineSmall = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.SemiBold,
        fontSize = 22.sp
    ),

    // ===================================================
    // TEXTO PRINCIPAL
    // ===================================================
    //
    // Utilizado para descripciones.
    //
    bodyLarge = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),

    // ===================================================
    // TEXTO PEQUEÑO
    // ===================================================
    //
    // Utilizado para lenguajes y detalles.
    //
    bodySmall = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Light,
        fontSize = 13.sp
    )
)