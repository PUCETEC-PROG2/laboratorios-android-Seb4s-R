package ec.edu.puce.githubclient.ui.components

// ======================================================
// IMPORTACIONES
// ======================================================

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue

import androidx.compose.ui.Modifier

import androidx.compose.ui.geometry.Offset

import androidx.compose.ui.graphics.Brush

import ec.edu.puce.githubclient.ui.theme.BrightTurquoise
import ec.edu.puce.githubclient.ui.theme.DeepOceanBlue
import ec.edu.puce.githubclient.ui.theme.ElectricBlue
import ec.edu.puce.githubclient.ui.theme.PuceBlue

// ======================================================
// FONDO ANIMADO
// ======================================================
//
// Este composable envuelve cualquier pantalla.
//
// Ejemplo:
//
// AnimatedGradientBackground {
//      RepoList()
// }
//
// Todo lo que esté dentro aparecerá sobre
// el fondo animado.
// ======================================================

@Composable
fun AnimatedGradientBackground(

    content: @Composable () -> Unit

) {

    // ==================================================
    // TRANSICIÓN INFINITA
    // ==================================================
    //
    // Se ejecuta continuamente mientras la pantalla
    // está activa.
    //
    val infiniteTransition = rememberInfiniteTransition(
        label = "backgroundAnimation"
    )

    // ==================================================
    // POSICIÓN INICIAL DEL GRADIENTE
    // ==================================================
    //
    // Va desde 0 hasta 1000.
    //
    val startOffset by infiniteTransition.animateFloat(

        initialValue = 0f,

        targetValue = 1000f,

        animationSpec = infiniteRepeatable(

            animation = tween(

                durationMillis = 15000,

                easing = LinearEasing
            ),

            repeatMode = RepeatMode.Reverse
        ),

        label = "startOffset"
    )

    // ==================================================
    // CONTENEDOR PRINCIPAL
    // ==================================================
    //
    // Dibuja el fondo animado.
    //
    Box(

        modifier = Modifier

            .fillMaxSize()

            .background(

                brush = Brush.linearGradient(

                    colors = listOf(

                        DeepOceanBlue,

                        PuceBlue,

                        ElectricBlue,

                        BrightTurquoise

                    ),

                    start = Offset(
                        x = startOffset,
                        y = 0f
                    ),

                    end = Offset(
                        x = startOffset + 1500f,
                        y = 1500f
                    )
                )
            )
    ) {

        // ==============================================
        // CONTENIDO
        // ==============================================

        content()
    }
}