package ec.edu.puce.githubclient.ui.screens

// ======================================================
// IMPORTACIONES
// ======================================================

import androidx.compose.foundation.layout.*

import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send

import androidx.compose.material3.*

import androidx.compose.runtime.*

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.graphics.Color

import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.tooling.preview.Preview

import androidx.compose.ui.unit.dp

import androidx.lifecycle.viewmodel.compose.viewModel

import ec.edu.puce.githubclient.ui.components.AnimatedGradientBackground

import ec.edu.puce.githubclient.ui.theme.BrightTurquoise
import ec.edu.puce.githubclient.ui.theme.GithubClientTheme

import ec.edu.puce.githubclient.viewmodels.RepoFormViewModel

// ======================================================
// API EXPERIMENTAL DE MATERIAL 3
// ======================================================

@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun RepoForm(

    onBackClick: () -> Unit = {},

    onSaveSuccess: () -> Unit = {},

    viewModel: RepoFormViewModel = viewModel()

) {

    // ==================================================
    // OBSERVAR ESTADOS DEL VIEWMODEL
    // ==================================================

    val isLoading by viewModel.isLoading.collectAsState()

    val errorMsg by viewModel.errorMsg.collectAsState()

    val isSuccess by viewModel.isSuccess.collectAsState()

    // ==================================================
    // ESTADOS DE LOS INPUTS
    // ==================================================

    var name by remember {

        mutableStateOf("")
    }

    var description by remember {

        mutableStateOf("")
    }

    // ==================================================
    // DETECTAR CREACIÓN EXITOSA
    // ==================================================

    LaunchedEffect(isSuccess) {

        if (isSuccess) {

            onSaveSuccess()

            viewModel.resetSuccess()
        }
    }

    // ==================================================
    // FONDO ANIMADO
    // ==================================================

    AnimatedGradientBackground {

        Scaffold(

            containerColor = Color.Transparent,

            // ==========================================
            // TOP BAR
            // ==========================================

            topBar = {

                TopAppBar(

                    title = {

                        Text(

                            text = "Nuevo Repositorio",

                            fontWeight = FontWeight.Bold
                        )
                    },

                    navigationIcon = {

                        IconButton(

                            onClick = onBackClick
                        ) {

                            Icon(

                                imageVector =
                                    Icons.Default.ArrowBack,

                                contentDescription =
                                    "Regresar"
                            )
                        }
                    },

                    colors = TopAppBarDefaults.topAppBarColors(

                        containerColor =
                            Color.Transparent,

                        titleContentColor =
                            Color.White,

                        navigationIconContentColor =
                            Color.White
                    )
                )
            }

        ) { innerPadding ->

            // ==========================================
            // CONTENEDOR PRINCIPAL
            // ==========================================

            Box(

                modifier = Modifier

                    .fillMaxSize()

                    .padding(innerPadding)

            ) {

                // ======================================
                // LOADING
                // ======================================

                if (isLoading) {

                    CircularProgressIndicator(

                        modifier = Modifier.align(
                            Alignment.Center
                        )
                    )
                }

                // ======================================
                // ERROR
                // ======================================

                else if (!errorMsg.isNullOrBlank()) {

                    Text(

                        text = errorMsg!!,

                        color = MaterialTheme.colorScheme.error,

                        modifier = Modifier.align(
                            Alignment.Center
                        )
                    )
                }

                // ======================================
                // FORMULARIO
                // ======================================

                else {

                    Card(

                        modifier = Modifier

                            .fillMaxWidth()

                            .padding(
                                horizontal = 20.dp
                            )

                            .align(
                                Alignment.Center
                            ),

                        shape = RoundedCornerShape(
                            32.dp
                        ),

                        colors = CardDefaults.cardColors(

                            containerColor =
                                Color.White.copy(
                                    alpha = 0.12f
                                )
                        )

                    ) {

                        Column(

                            modifier = Modifier.padding(
                                24.dp
                            )
                        ) {

                            // ==========================
                            // TÍTULO
                            // ==========================

                            Text(

                                text =
                                    "Crear repositorio",

                                style =
                                    MaterialTheme.typography.headlineSmall,

                                color = Color.White
                            )

                            Spacer(

                                modifier =
                                    Modifier.height(24.dp)
                            )

                            // ==========================
                            // INPUT NOMBRE
                            // ==========================

                            OutlinedTextField(

                                value = name,

                                onValueChange = {
                                    name = it
                                },

                                label = {
                                    Text("Nombre")
                                },

                                modifier = Modifier.fillMaxWidth(),

                                shape = RoundedCornerShape(18.dp),

                                singleLine = true,

                                colors = OutlinedTextFieldDefaults.colors(

                                    focusedBorderColor = BrightTurquoise,

                                    unfocusedBorderColor = Color.White.copy(alpha = 0.4f),

                                    focusedLabelColor = BrightTurquoise,

                                    unfocusedLabelColor = Color.White.copy(alpha = 0.7f),

                                    focusedTextColor = Color.White,

                                    unfocusedTextColor = Color.White,

                                    cursorColor = BrightTurquoise
                                )
                            )

                            Spacer(

                                modifier =
                                    Modifier.height(16.dp)
                            )

                            // ==========================
                            // INPUT DESCRIPCIÓN
                            // ==========================

                            OutlinedTextField(

                                value = description,

                                onValueChange = {

                                    description = it
                                },

                                label = {

                                    Text(
                                        "Descripción"
                                    )
                                },

                                modifier =
                                    Modifier.fillMaxWidth(),

                                shape =
                                    RoundedCornerShape(
                                        18.dp
                                    ),

                                minLines = 5
                            )

                            Spacer(

                                modifier =
                                    Modifier.height(24.dp)
                            )

                            // ==========================
                            // BOTÓN GUARDAR
                            // ==========================

                            Button(

                                onClick = {

                                    viewModel.createRepo(
                                        name,
                                        description
                                    )
                                },

                                modifier =
                                    Modifier.fillMaxWidth(),

                                shape =
                                    RoundedCornerShape(
                                        18.dp
                                    ),

                                colors =
                                    ButtonDefaults.buttonColors(

                                        containerColor =
                                            BrightTurquoise
                                    )
                            ) {

                                Icon(

                                    imageVector =
                                        Icons.Default.Send,

                                    contentDescription =
                                        "Guardar"
                                )

                                Spacer(

                                    modifier =
                                        Modifier.width(
                                            12.dp
                                        )
                                )

                                Text(

                                    text = "Guardar"
                                )
                            }
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
fun RepoFormPreview() {

    GithubClientTheme {

        RepoForm()
    }
}