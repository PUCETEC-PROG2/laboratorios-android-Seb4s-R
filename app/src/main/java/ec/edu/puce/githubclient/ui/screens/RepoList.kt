package ec.edu.puce.githubclient.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ec.edu.puce.githubclient.models.Repository
import ec.edu.puce.githubclient.ui.components.AnimatedGradientBackground
import ec.edu.puce.githubclient.ui.components.RepoItem
import ec.edu.puce.githubclient.ui.theme.BrightTurquoise
import ec.edu.puce.githubclient.ui.theme.GithubClientTheme
import ec.edu.puce.githubclient.viewmodels.RepoFormViewModel
import ec.edu.puce.githubclient.viewmodels.RepoListViewModel

/**
 * Pantalla principal que muestra la lista de repositorios.
 * Implementa Snackbar para errores y diálogos de confirmación mejorados.
 */
@Composable
fun RepoList(
    modifier: Modifier = Modifier,
    viewModel: RepoListViewModel = viewModel(),
    formViewModel: RepoFormViewModel = viewModel(),
    onNavigateToForm: (Repository?) -> Unit = {}
) {
    val repos by viewModel.repos.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val listErrorMsg by viewModel.errorMsg.collectAsState()

    val isFormLoading by formViewModel.isLoading.collectAsState()
    val formErrorMsg by formViewModel.errorMsg.collectAsState()
    val isFormSuccess by formViewModel.isSuccess.collectAsState()

    var repoToDelete by remember { mutableStateOf<Repository?>(null) }
    var showDeleteDialog by remember { mutableStateOf(false) }

    // Manejo de Snackbar para errores
    val snackbarHostState = remember { SnackbarHostState() }

    // Observar errores y mostrarlos en Snackbar
    LaunchedEffect(formErrorMsg, listErrorMsg) {
        val error = formErrorMsg ?: listErrorMsg
        if (error != null) {
            snackbarHostState.showSnackbar(
                message = error,
                duration = SnackbarDuration.Long,
                withDismissAction = true
            )
            formViewModel.resetError()
            viewModel.resetError()
        }
    }

    // Refrescar lista al tener éxito
    LaunchedEffect(isFormSuccess) {
        if (isFormSuccess) {
            viewModel.fetchRepos()
            formViewModel.resetSuccess()
        }
    }

    AnimatedGradientBackground {
        Scaffold(
            containerColor = Color.Transparent,
            snackbarHost = { SnackbarHost(snackbarHostState) },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { onNavigateToForm(null) },
                    shape = CircleShape,
                    containerColor = BrightTurquoise,
                    contentColor = Color.White
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Agregar")
                }
            }
        ) { innerPadding ->
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                // CONTENIDO PRINCIPAL
                if (!isLoading) {
                    // Comentario: Si la lista de repositorios está vacía, mostramos el estado vacío. El error se maneja en el Snackbar de forma independiente.
                    if (repos.isEmpty()) {
                        EmptyState(modifier = Modifier.align(Alignment.Center))
                    } else {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(top = 24.dp, bottom = 100.dp)
                        ) {
                            item { RepoListHeader() }
                            items(repos) { repo ->
                                RepoItem(
                                    repository = repo,
                                    onEditClick = { onNavigateToForm(it) },
                                    onDeleteClick = {
                                        repoToDelete = it
                                        showDeleteDialog = true
                                    }
                                )
                            }
                        }
                    }
                }

                // INDICADOR DE CARGA CENTRAL
                if (isLoading || isFormLoading) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Black.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = BrightTurquoise)
                    }
                }
            }
        }
    }

    // Comentario: Diálogo de confirmación para eliminar repositorio
    if (showDeleteDialog && repoToDelete != null) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { 
                Text(
                    text = "Eliminar repositorio",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = Color.White // Comentario: Color blanco explícito para contraste
                ) 
            },
            text = { 
                Text(
                    text = "¿Tienes la certeza de querer eliminar este repositorio? Esta acción no se puede deshacer.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White.copy(alpha = 0.85f) // Comentario: Texto blanco semi-transparente para legibilidad
                ) 
            },
            confirmButton = {
                Button(
                    onClick = {
                        repoToDelete?.let {
                            formViewModel.deleteRepo(it.owner.login, it.name)
                        }
                        showDeleteDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF5252))
                ) {
                    Text("Eliminar", color = Color.White, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showDeleteDialog = false },
                    colors = ButtonDefaults.textButtonColors(contentColor = Color.White.copy(alpha = 0.7f)) // Comentario: Color gris claro/blanco para contraste
                ) {
                    Text("Cancelar")
                }
            },
            shape = RoundedCornerShape(24.dp),
            containerColor = Color(0xFF16253B) // Comentario: Fondo oscuro sólido de alto contraste
        )
    }
}

@Composable
fun EmptyState(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Default.Info,
            contentDescription = null,
            modifier = Modifier.size(64.dp),
            tint = Color.White.copy(alpha = 0.5f)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "No tienes repositorios públicos",
            style = MaterialTheme.typography.titleMedium,
            color = Color.White,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun RepoListHeader() {
    Column(modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp)) {
        Text(
            text = "Mis Repositorios",
            style = MaterialTheme.typography.headlineMedium,
            color = Color.White,
            fontWeight = FontWeight.Black
        )
        Text(
            text = "Gestiona tus proyectos de GitHub",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White.copy(alpha = 0.7f)
        )
        Spacer(modifier = Modifier.height(8.dp))
        HorizontalDivider(color = Color.White.copy(alpha = 0.1f), thickness = 1.dp)
    }
}

@Preview(showBackground = true)
@Composable
fun RepoListPreview() {
    GithubClientTheme {
        RepoList()
    }
}
