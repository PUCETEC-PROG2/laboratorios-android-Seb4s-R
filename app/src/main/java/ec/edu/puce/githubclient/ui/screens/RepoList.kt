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
            avatarUrl= "https://www.google.com/url?sa=t&source=web&rct=j&url=https%3A%2F%2Fautodesignmagazine.com%2Fen%2F2018%2F06%2Fnissan-gtr-50-by-italdesign%2F&ved=0CBYQjRxqFwoTCKCigqqSt5QDFQAAAAAdAAAAABAb&opi=89978449",
            language = "Kotlin"
        )
        RepoItem(
            name = "Repositorio 2 de Android",
            description = "Repositorio 2 creado en Kotlin",
            avatarUrl= "https://www.google.com/url?sa=t&source=web&rct=j&url=https%3A%2F%2Fes.motor1.com%2Fnews%2F386307%2Fnissan-gtr50-italdesign-produccion-2020%2F&ved=0CBYQjRxqFwoTCKCigqqSt5QDFQAAAAAdAAAAABA2&opi=89978449",
            language = "Kotlin"
        )
        RepoItem(
            name = "Repositorio 3 de Android",
            description = "Repositorio 2 creado en Kotlin",
            avatarUrl= "https://www.google.com/url?sa=t&source=web&rct=j&url=https%3A%2F%2Fdiecastsociety.com%2Fphoto-galery-autoart-nissan-gt-r50-italdesign-good-wood%2F&ved=0CBYQjRxqGAoTCKCigqqSt5QDFQAAAAAdAAAAABCSAQ&opi=89978449",
            language = "Kotlin"
        )
    }
}

