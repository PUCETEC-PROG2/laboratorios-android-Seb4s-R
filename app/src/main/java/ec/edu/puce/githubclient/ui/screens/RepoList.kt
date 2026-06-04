package ec.edu.puce.githubclient.ui.screens

// ======================================================
// IMPORTACIONES
// ======================================================

import androidx.compose.foundation.layout.*

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

import androidx.compose.foundation.shape.CircleShape

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.graphics.Color

import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.tooling.preview.Preview

import androidx.compose.ui.unit.dp

import androidx.lifecycle.viewmodel.compose.viewModel

import ec.edu.puce.githubclient.ui.components.AnimatedGradientBackground
import ec.edu.puce.githubclient.ui.components.RepoItem

import ec.edu.puce.githubclient.ui.theme.BrightTurquoise
import ec.edu.puce.githubclient.ui.theme.GithubClientTheme

import ec.edu.puce.githubclient.viewmodels.RepoListViewModel

// ======================================================
// PANTALLA PRINCIPAL
// ======================================================

@Composable
fun RepoList(

    modifier: Modifier = Modifier,

    viewModel: RepoListViewModel = viewModel(),

    onNavigateToForm: () -> Unit = {}

) {

    // ==================================================
    // OBSERVAR ESTADOS DEL VIEWMODEL
    // ==================================================

    val repos by viewModel.repos.collectAsState()

    val isLoading by viewModel.isLoading.collectAsState()

    val errorMsg by viewModel.errorMsg.collectAsState()

    // ==================================================
    // FONDO ANIMADO
    // ==================================================

    AnimatedGradientBackground {

        Scaffold(

            containerColor = Color.Transparent,

            floatingActionButton = {

                FloatingActionButton(

                    onClick = onNavigateToForm,

                    shape = CircleShape,

                    containerColor = BrightTurquoise,

                    contentColor = Color.White

                ) {

                    Icon(

                        imageVector = Icons.Default.Add,

                        contentDescription = "Agregar repositorio"
                    )
                }
            }

        ) { innerPadding ->

            Box(

                modifier = modifier

                    .fillMaxSize()

                    .padding(innerPadding)

            ) {

                // ==========================================
                // LOADING
                // ==========================================

                if (isLoading) {

                    CircularProgressIndicator(

                        modifier = Modifier.align(
                            Alignment.Center
                        )
                    )
                }

                // ==========================================
                // ERROR
                // ==========================================

                errorMsg?.let {

                    Text(

                        text = it,

                        color = MaterialTheme.colorScheme.error,

                        modifier = Modifier

                            .align(Alignment.Center)

                            .padding(16.dp)
                    )
                }

                // ==========================================
                // CONTENIDO
                // ==========================================

                if (!isLoading && errorMsg.isNullOrBlank()) {

                    LazyColumn(

                        modifier = Modifier.fillMaxSize(),

                        contentPadding = PaddingValues(

                            top = 32.dp,

                            bottom = 120.dp
                        )

                    ) {

                        // ==================================
                        // ENCABEZADO
                        // ==================================

                        item {

                            Column(

                                modifier = Modifier
                                    .padding(
                                        horizontal = 24.dp
                                    )
                            ) {

                                Text(

                                    text = "GitHub Client",

                                    style =
                                        MaterialTheme.typography.titleLarge,

                                    color = Color.White,

                                    fontWeight = FontWeight.Bold
                                )

                                Spacer(
                                    modifier =
                                        Modifier.height(6.dp)
                                )

                                Text(

                                    text =
                                        "Explora y administra tus repositorios",

                                    style =
                                        MaterialTheme.typography.bodyLarge,

                                    color =
                                        Color.White.copy(
                                            alpha = 0.80f
                                        )
                                )

                                Spacer(
                                    modifier =
                                        Modifier.height(24.dp)
                                )
                            }
                        }

                        // ==================================
                        // LISTA DE REPOSITORIOS
                        // ==================================

                        items(repos) { repo ->

                            RepoItem(
                                repository = repo
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
fun RepoListPreview() {

    GithubClientTheme {

        RepoList()
    }
}