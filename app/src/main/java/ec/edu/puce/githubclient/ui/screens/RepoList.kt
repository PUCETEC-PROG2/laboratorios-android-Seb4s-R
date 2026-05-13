package ec.edu.puce.githubclient.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ec.edu.puce.githubclient.ui.components.RepoItem

@Composable
fun RepoList(
    modifier: Modifier = Modifier
) {
    Column (
        modifier
    ){

        RepoItem(
            name = "Repositorio 1 de Android",
            description = "Repositorio 1 creado en Kotlin",
            avatarUrl= "https://diecastsociety.com/wp-content/uploads/2025/02/aa_GTR50con-1.jpg",
            language = "Kotlin"
        )
        RepoItem(
            name = "Repositorio 2 de Android",
            description = "Repositorio 2 creado en Kotlin",
            avatarUrl= "https://diecastsociety.com/wp-content/uploads/2025/02/aa_GTR50con-5.jpg",
            language = "Kotlin"
        )
        RepoItem(
            name = "Repositorio 3 de Android",
            description = "Repositorio 2 creado en Kotlin",
            avatarUrl= "https://diecastsociety.com/wp-content/uploads/2025/02/aa_GTR50con-15.jpg",
            language = "Kotlin"
        )
    }
}

