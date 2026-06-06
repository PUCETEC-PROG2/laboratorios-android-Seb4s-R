package ec.edu.puce.githubclient.ui.screens

import androidx.compose.foundation.background
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

/**
 * Pantalla de formulario optimizada.
 * RESTRICCIÓN: El nombre del repositorio es inmutable en modo edición.
 * Implementa Snackbar para retroalimentación de errores.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RepoForm(
    onBackClick: () -> Unit = {},
    onSaveSuccess: () -> Unit = {},
    viewModel: RepoFormViewModel = viewModel()
) {
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMsg by viewModel.errorMsg.collectAsState()
    val isSuccess by viewModel.isSuccess.collectAsState()
    val editingRepo by viewModel.editingRepo.collectAsState()

    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    
    val snackbarHostState = remember { SnackbarHostState() }

    val isEditing = editingRepo != null
    val screenTitle = if (isEditing) "Editar Repositorio" else "Nuevo Repositorio"
    val buttonText = if (isEditing) "Actualizar descripción" else "Guardar repositorio"

    // Comentario: Cargar datos del repositorio si se edita, o limpiar los campos si se crea uno nuevo
    LaunchedEffect(editingRepo) {
        if (editingRepo != null) {
            name = editingRepo!!.name
            description = editingRepo!!.description ?: ""
        } else {
            name = ""
            description = ""
        }
    }

    LaunchedEffect(errorMsg) {
        errorMsg?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.resetError()
        }
    }

    LaunchedEffect(isSuccess) {
        if (isSuccess) {
            onSaveSuccess()
            viewModel.resetSuccess()
        }
    }

    AnimatedGradientBackground {
        Scaffold(
            containerColor = Color.Transparent,
            snackbarHost = { SnackbarHost(snackbarHostState) },
            topBar = {
                TopAppBar(
                    title = { Text(text = screenTitle, fontWeight = FontWeight.Bold) },
                    navigationIcon = {
                        IconButton(onClick = onBackClick) {
                            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Regresar")
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent,
                        titleContentColor = Color.White,
                        navigationIconContentColor = Color.White
                    )
                )
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                        .align(Alignment.Center),
                    shape = RoundedCornerShape(32.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White.copy(alpha = 0.12f)
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Column(modifier = Modifier.padding(24.dp)) {
                        Text(
                            text = if (isEditing) "Modificar detalles" else "Información del repo",
                            style = MaterialTheme.typography.titleLarge,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        // Comentario: Campo de nombre del repositorio. Queda inhabilitado (solo lectura) al editar para no cambiar el nombre.
                        OutlinedTextField(
                            value = name,
                            onValueChange = { if (!isEditing) name = it },
                            label = { Text("Nombre del repositorio") },
                            modifier = Modifier.fillMaxWidth(),
                            enabled = !isEditing,
                            readOnly = isEditing,
                            shape = RoundedCornerShape(18.dp),
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = BrightTurquoise,
                                unfocusedBorderColor = Color.White.copy(alpha = 0.4f),
                                disabledBorderColor = Color.White.copy(alpha = 0.2f), // Comentario: Borde visible en deshabilitado
                                disabledTextColor = Color.White.copy(alpha = 0.6f), // Comentario: Texto legible (contraste mejorado)
                                focusedLabelColor = BrightTurquoise,
                                unfocusedLabelColor = Color.White.copy(alpha = 0.7f),
                                disabledLabelColor = Color.White.copy(alpha = 0.5f), // Comentario: Label legible en deshabilitado
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White,
                                cursorColor = BrightTurquoise
                            )
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // CAMPO DESCRIPCIÓN
                        OutlinedTextField(
                            value = description,
                            onValueChange = { description = it },
                            label = { Text("Descripción (Opcional)") },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(18.dp),
                            minLines = 3,
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

                        Spacer(modifier = Modifier.height(32.dp))

                        Button(
                            onClick = {
                                if (isEditing && editingRepo != null) {
                                    viewModel.updateRepo(
                                        owner = editingRepo!!.owner.login,
                                        oldName = editingRepo!!.name,
                                        description = description
                                    )
                                } else {
                                    viewModel.createRepo(name, description)
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp),
                            shape = RoundedCornerShape(18.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = BrightTurquoise),
                            enabled = name.isNotBlank() && !isLoading
                        ) {
                            if (isLoading) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(24.dp),
                                    color = Color.White,
                                    strokeWidth = 2.dp
                                )
                            } else {
                                Icon(imageVector = Icons.Default.Send, contentDescription = null)
                                Spacer(modifier = Modifier.width(12.dp))
                                Text(text = buttonText, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RepoFormPreview() {
    GithubClientTheme {
        RepoForm()
    }
}
