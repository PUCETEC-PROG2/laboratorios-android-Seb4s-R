package ec.edu.puce.githubclient.ui.components

// ======================================================
// IMPORTACIONES
// ======================================================

import androidx.compose.foundation.border

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.expandVertically

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.draw.clip

import androidx.compose.ui.graphics.Color

import androidx.compose.ui.layout.ContentScale

import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.tooling.preview.Preview

import androidx.compose.ui.unit.dp

import coil.compose.AsyncImage

import ec.edu.puce.githubclient.models.GithubUser
import ec.edu.puce.githubclient.models.Repository

import ec.edu.puce.githubclient.ui.theme.BrightTurquoise
import ec.edu.puce.githubclient.ui.theme.GlassBorder
import ec.edu.puce.githubclient.ui.theme.GlassWhite
import ec.edu.puce.githubclient.ui.theme.TextMuted

// ======================================================
// REPOSITORIO INDIVIDUAL
// ======================================================
//
// Representa una tarjeta de repositorio.
//
// Se utilizará dentro de:
//
// LazyColumn
//
// RepoList
//
// ======================================================

@Composable
fun RepoItem(

    repository: Repository

) {

    // ==================================================
    // TARJETA PRINCIPAL
    // ==================================================
    //
    // Simula cristal utilizando transparencia.
    //

    AnimatedVisibility(

        visible = true,

        enter = fadeIn() + expandVertically()

    ) {

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 12.dp,
                    vertical = 8.dp
                ),
            shape = RoundedCornerShape(28.dp),
            colors = CardDefaults.cardColors(
                containerColor = GlassWhite
            )) {

            Row(

                modifier = Modifier
                    .fillMaxWidth()
                    .padding(18.dp),

                verticalAlignment = Alignment.CenterVertically

            ) {

            // ==========================================
            // AVATAR DEL USUARIO
            // ==========================================

            AsyncImage(

                model = repository.owner.avatarUrl,

                contentDescription =
                    "Avatar de ${repository.owner.login}",

                modifier = Modifier

                    .size(72.dp)

                    .clip(CircleShape)

                    .border(
                        width = 2.dp,
                        color = BrightTurquoise,
                        shape = CircleShape
                    ),

                contentScale = ContentScale.Crop
            )

            Spacer(
                modifier = Modifier.width(18.dp)
            )

            // ==========================================
            // INFORMACIÓN DEL REPOSITORIO
            // ==========================================

            Column(
                modifier = Modifier.weight(1f)
            ) {

                // ======================================
                // NOMBRE
                // ======================================

                Text(

                    text = repository.name,

                    style = MaterialTheme.typography.titleMedium,

                    fontWeight = FontWeight.Bold
                )

                Spacer(
                    modifier = Modifier.height(6.dp)
                )

                // ======================================
                // DESCRIPCIÓN
                // ======================================

                if (!repository.description.isNullOrBlank()) {

                    Text(

                        text = repository.description,

                        style = MaterialTheme.typography.bodySmall,

                        color = TextMuted
                    )
                }

                Spacer(
                    modifier = Modifier.height(10.dp)
                )

                // ======================================
                // BADGE DEL LENGUAJE
                // ======================================

                val language = repository.language
                if (!language.isNullOrBlank()) {
                    val languageColor = when (
                        language.lowercase()
                    ) {

                        "kotlin" -> Color(0xFF7F52FF)

                        "java" -> Color(0xFFF89820)

                        "javascript" -> Color(0xFFF7DF1E)

                        "typescript" -> Color(0xFF3178C6)

                        "python" -> Color(0xFF3776AB)

                        else -> BrightTurquoise
                    }

                    Surface(

                        shape = RoundedCornerShape(50.dp),

                        color = languageColor.copy(
                            alpha = 0.20f
                        ),

                        modifier = Modifier
                    ) {

                        Text(

                            text = language,

                            modifier = Modifier.padding(
                                horizontal = 14.dp,
                                vertical = 6.dp
                            ),

                            color = languageColor,

                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }
    }
}
}

// ======================================================
// PREVIEW
// ======================================================

@Preview(showBackground = true)

@Composable
fun RepoItemPreview() {

    val repository = Repository(

        id = "12345",

        name = "Repositorio Android",

        description =
            "Aplicación desarrollada con Jetpack Compose y Retrofit.",

        language = "Kotlin",

        owner = GithubUser(

            id = "123",

            login = "SebastianRojas",

            avatarUrl =
                "https://pucetec.puce.edu.ec/2026/pluginfile.php/485/user/icon/puce/f2?rev=20914"
        )
    )

    RepoItem(
        repository = repository
    )
}