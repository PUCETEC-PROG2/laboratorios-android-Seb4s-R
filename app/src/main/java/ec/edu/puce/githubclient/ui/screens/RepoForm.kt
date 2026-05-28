package ec.edu.puce.githubclient.ui.screens

// ==============================
// IMPORTACIONES
// ==============================

// Componentes para diseño visual y layouts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width

// Permite bordes redondeados
import androidx.compose.foundation.shape.RoundedCornerShape

// Iconos de Material Design
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button

// Componentes visuales Material3
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults

// Funciones de Compose
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment

// Modificadores visuales
import androidx.compose.ui.Modifier

// Sombra visual
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout

// Preview para Android Studio
import androidx.compose.ui.tooling.preview.Preview

// Unidades visuales
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

// Tema personalizado de la app
import ec.edu.puce.githubclient.ui.theme.GithubClientTheme
import ec.edu.puce.githubclient.viewmodels.RepoFormViewModel


// ==============================
// PANTALLA PRINCIPAL
// ==============================

// Habilita APIs experimentales de Material3
@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun RepoForm(
    onBackClick: () -> Unit = {},
    onSaveSuccess: () -> Unit = {},
    viewModel: RepoFormViewModel = viewModel ()
) {
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMsg by viewModel.errorMsg.collectAsState()
    val isSuccess by viewModel.isSuccess.collectAsState()

    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    LaunchedEffect(isSuccess)  {
        if (isSuccess) {
            onSaveSuccess()
            viewModel.resetSuccess()
        }
    }

    // ==============================
    // ESTRUCTURA BASE DE LA PANTALLA
    // ==============================

    Scaffold(
        // ==============================
        // BARRA SUPERIOR (TOP APP BAR)
        // ==============================

        topBar = {
            TopAppBar(
                // Título principal de la pantalla
                title = {
                    Text(
                        text = "Crear Repositorio",
                        // Tamaño del texto
                        fontSize = 20.sp
                    )
                },
                // Ícono de regreso
                navigationIcon = {
                    IconButton(
                        onClick = onBackClick
                    ){
                        Icon(
                            // Flecha hacia atrás
                            imageVector = Icons.Default.ArrowBack,
                            // Texto para accesibilidad
                            contentDescription = "Regresar",
                            // Espaciado izquierdo
                            modifier = Modifier.padding(start = 12.dp)
                        )
                    }
                },

                // ==============================
                // COLORES DE LA TOP BAR
                // ==============================
                colors = TopAppBarDefaults.topAppBarColors(
                    // Color de fondo de la barra
                    // Normalmente el color principal del tema
                    containerColor = MaterialTheme.colorScheme.surface,
                    // Color del texto del título
                    titleContentColor = MaterialTheme.colorScheme.tertiary,
                    // Color del ícono de regreso
                    navigationIconContentColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { innerPadding ->

        // ==============================
        // CONTENEDOR PRINCIPAL
        // ==============================

        Column(
            modifier = Modifier
                // Ocupa toda la pantalla
                .fillMaxSize()
                // Color de fondo general
                .background(
                    MaterialTheme.colorScheme.primary
                )
                // Respeta espacios del Scaffold
                .padding(innerPadding)
                // Espaciado horizontal
                .padding(horizontal = 20.dp),
            // Centra verticalmente el contenido
            verticalArrangement = Arrangement.Center

        ) {

            if (isLoading) {
                CircularProgressIndicator(
                    Modifier.align(Alignment.CenterHorizontally)
                )
            } else if (!errorMsg.isNullOrBlank()) {
                Text(
                    text = errorMsg!!,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align (Alignment.CenterHorizontally)
                )
            } else {

                // ==============================
                // TARJETA VISUAL (CARD)
                // ==============================

                Card(
                    modifier = Modifier
                        // Ocupa todo el ancho
                        .fillMaxWidth()
                        // Sombra para profundidad visual
                        .shadow(
                            // Intensidad de sombra
                            elevation = 10.dp,
                            // Bordes redondeados
                            shape = RoundedCornerShape(24.dp)
                        ),
                    // Bordes suaves y modernos
                    shape = RoundedCornerShape(24.dp),

                    // ==============================
                    // COLORES DE LA CARD
                    // ==============================
                    colors = CardDefaults.cardColors(
                        // Color del fondo de la tarjeta
                        containerColor =
                            MaterialTheme.colorScheme.surface
                    )
                ) {

                    // ==============================
                    // CONTENIDO INTERNO DE LA CARD
                    // ==============================
                    Column(
                        // Espaciado interno
                        modifier = Modifier.padding(24.dp)
                    ) {

                        // ==============================
                        // TÍTULO SECUNDARIO
                        // ==============================
                        Text(
                            text = "Nuevo repositorio",
                            // Estilo grande de Material3
                            style =
                                MaterialTheme.typography.headlineSmall,
                            // Color principal del tema
                            color =
                                MaterialTheme.colorScheme.primary
                        )
                        // Espacio vertical
                        Spacer(
                            modifier = Modifier.height(24.dp)
                        )

                        // ==============================
                        // INPUT: NOMBRE DEL REPOSITORIO
                        // ==============================
                        OutlinedTextField(
                            value = name,
                            onValueChange = { name = it },
                            // Texto guía
                            label = {
                                Text("Nombre del repositorio")
                            },
                            // Ocupa todo el ancho
                            modifier = Modifier.fillMaxWidth(),
                            // Bordes redondeados
                            shape = RoundedCornerShape(16.dp),
                            // Solo una línea
                            singleLine = true
                        )
                        // Espacio entre inputs
                        Spacer(
                            modifier = Modifier.height(20.dp)
                        )

                        // ==============================
                        // INPUT: DESCRIPCIÓN
                        // ==============================

                        OutlinedTextField(

                            value = description,

                            onValueChange = { description = it },

                            // Texto guía
                            label = {
                                Text("Descripción del repositorio")
                            },

                            // Ocupa todo el ancho
                            modifier = Modifier.fillMaxWidth(),

                            // Bordes suaves
                            shape = RoundedCornerShape(16.dp),

                            // Varias líneas
                            minLines = 5
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = { viewModel.createRepo(name, description) },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(
                                imageVector = Icons.Default.Send,
                                contentDescription = "Guardar"
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Text("Guardar")
                        }
                    }
                }
            }
        }
    }
}


// ==============================
// PREVIEW PARA ANDROID STUDIO
// ==============================

@Preview(showBackground = true)

@Composable
fun RepoFormPreview() {

    // Aplica el tema visual personalizado
    GithubClientTheme {

        // Muestra la pantalla
        RepoForm()
    }
}