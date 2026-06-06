package ec.edu.puce.githubclient.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import ec.edu.puce.githubclient.models.GithubUser
import ec.edu.puce.githubclient.models.Repository
import ec.edu.puce.githubclient.ui.theme.BrightTurquoise
import ec.edu.puce.githubclient.ui.theme.ElectricBlue
import ec.edu.puce.githubclient.ui.theme.GlassBorder
import ec.edu.puce.githubclient.ui.theme.GlassWhiteStrong
import ec.edu.puce.githubclient.ui.theme.TextPrimary
import ec.edu.puce.githubclient.ui.theme.TextSecondary

/**
 * Componente de tarjeta de repositorio mejorado visualmente.
 * Implementa jerarquía visual clara, mejor contraste y área táctil optimizada.
 */
// Comentario: Definición de la tarjeta de repositorio con Material 3 y Glassmorphic design
@Composable
fun RepoItem(
    repository: Repository,
    onEditClick: (Repository) -> Unit = {},
    onDeleteClick: (Repository) -> Unit = {}
) {
    // Comentario: Animación para que el elemento aparezca de forma suave
    AnimatedVisibility(
        visible = true,
        enter = fadeIn() + expandVertically()
    ) {
        // Comentario: Tarjeta principal con bordes redondeados y borde de cristal para simular Glassmorphism
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp) // Comentario: Margen vertical reducido para mejor densidad
                .border(
                    width = 1.dp,
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.White.copy(alpha = 0.18f),
                            Color.White.copy(alpha = 0.03f)
                        )
                    ),
                    shape = RoundedCornerShape(16.dp)
                ),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White.copy(alpha = 0.08f) // Comentario: Fondo semi-transparente para Glassmorphism
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            // Comentario: Contenedor interno con padding optimizado
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Comentario: Avatar del dueño del repositorio con borde brillante turquesa
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(56.dp) // Comentario: Tamaño de avatar optimizado
                        .clip(CircleShape)
                        .background(BrightTurquoise.copy(alpha = 0.2f))
                        .border(1.5.dp, BrightTurquoise, CircleShape)
                ) {
                    AsyncImage(
                        model = repository.owner.avatarUrl,
                        contentDescription = "Avatar de ${repository.owner.login}",
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                // Comentario: Información del repositorio (Nombre, Descripción, Lenguaje)
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    // Comentario: Nombre del repositorio con alto contraste y peso ExtraBold
                    Text(
                        text = repository.name,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    // Comentario: Descripción con contraste mejorado usando 85% de opacidad y bodyMedium
                    if (!repository.description.isNullOrBlank()) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = repository.description,
                            style = MaterialTheme.typography.bodyMedium, // Comentario: Cambiado de bodySmall a bodyMedium para legibilidad
                            color = Color.White.copy(alpha = 0.85f), // Comentario: Alto contraste sobre fondo oscuro
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            lineHeight = MaterialTheme.typography.bodyMedium.lineHeight * 1.2
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Comentario: Badge del lenguaje si está definido
                    repository.language?.let { lang ->
                        LanguageBadge(language = lang)
                    }
                }

                Spacer(modifier = Modifier.width(12.dp))

                // Comentario: Botones de acción lateral con áreas táctiles accesibles (48dp)
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Comentario: Botón para editar
                    FilledIconButton(
                        onClick = { onEditClick(repository) },
                        colors = IconButtonDefaults.filledIconButtonColors(
                            containerColor = BrightTurquoise.copy(alpha = 0.15f),
                            contentColor = BrightTurquoise
                        ),
                        modifier = Modifier.size(48.dp) // Comentario: Área táctil aumentada a 48dp para cumplir accesibilidad
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Editar repositorio",
                            modifier = Modifier.size(22.dp)
                        )
                    }

                    // Comentario: Botón para eliminar
                    FilledIconButton(
                        onClick = { onDeleteClick(repository) },
                        colors = IconButtonDefaults.filledIconButtonColors(
                            containerColor = Color(0xFFFF5252).copy(alpha = 0.15f),
                            contentColor = Color(0xFFFF5252)
                        ),
                        modifier = Modifier.size(48.dp) // Comentario: Área táctil aumentada a 48dp para cumplir accesibilidad
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Eliminar repositorio",
                            modifier = Modifier.size(22.dp)
                        )
                    }
                }
            }
        }
    }
}

// Comentario: Badge de lenguaje de programación rediseñado con alto contraste y punto de color
@Composable
fun LanguageBadge(language: String) {
    // Comentario: Mapeo de colores brillantes para cada lenguaje común para asegurar contraste
    val color = when (language.lowercase()) {
        "kotlin" -> Color(0xFFD4BFFF) // Comentario: Púrpura pastel de alto contraste
        "java" -> Color(0xFFFFCC80) // Comentario: Naranja pastel
        "javascript" -> Color(0xFFFFEB3B) // Comentario: Amarillo brillante
        "typescript" -> Color(0xFF90CAF9) // Comentario: Azul pastel
        "python" -> Color(0xFFA5D6A7) // Comentario: Verde pastel
        else -> BrightTurquoise // Comentario: Turquesa brillante por defecto
    }

    // Comentario: Badge contenedor con fondo semi-transparente y borde coloreado
    Box(
        modifier = Modifier
            .background(
                color = color.copy(alpha = 0.12f),
                shape = RoundedCornerShape(8.dp)
            )
            .border(
                width = 1.dp,
                color = color.copy(alpha = 0.35f),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(horizontal = 10.dp, vertical = 4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            // Comentario: Círculo de color que representa el lenguaje
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .background(color = color, shape = CircleShape)
            )
            // Comentario: Texto del lenguaje en blanco sólido para máxima legibilidad
            Text(
                text = language,
                color = Color.White,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RepoItemPreview() {
    val repository = Repository(
        id = "1",
        name = "Nombre del Repo",
        description = "Esta es una descripción de ejemplo con mejor contraste y diseño.",
        language = "Kotlin",
        owner = GithubUser("1", "User", "https://avatars.githubusercontent.com/u/1?v=4")
    )
    RepoItem(repository = repository)
}
