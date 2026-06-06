package ec.edu.puce.githubclient

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import ec.edu.puce.githubclient.ui.screens.RepoForm
import ec.edu.puce.githubclient.ui.screens.RepoList
import ec.edu.puce.githubclient.ui.theme.GithubClientTheme
import ec.edu.puce.githubclient.viewmodels.RepoFormViewModel
import ec.edu.puce.githubclient.viewmodels.RepoListViewModel

/**
 * Actividad principal de la aplicación.
 * Gestiona la navegación básica entre la lista de repositorios y el formulario.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GithubClientTheme {
                // Estado de navegación simple
                var currentScreen by remember { mutableStateOf("repoList") }
                
                // Instancias compartidas de ViewModels
                val listViewModel: RepoListViewModel = viewModel()
                val formViewModel: RepoFormViewModel = viewModel()

                when (currentScreen) {
                    "repoList" -> RepoList(
                        viewModel = listViewModel,
                        formViewModel = formViewModel,
                        onNavigateToForm = { repo ->
                            // Si se pasa un repo, entramos en modo edición
                            formViewModel.setEditingRepo(repo)
                            formViewModel.resetError()
                            currentScreen = "repoForm"
                        }
                    )
                    "repoForm" -> RepoForm(
                        viewModel = formViewModel,
                        onBackClick = { 
                            // Comentario: Resetear el estado de edición al salir
                            formViewModel.setEditingRepo(null)
                            currentScreen = "repoList" 
                        },
                        onSaveSuccess = {
                            // Comentario: Resetear el estado de edición al guardar con éxito
                            formViewModel.setEditingRepo(null)
                            listViewModel.fetchRepos()
                            currentScreen = "repoList"
                        }
                    )
                }
            }
        }
    }
}
