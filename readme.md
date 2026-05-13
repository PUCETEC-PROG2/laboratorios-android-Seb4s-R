# Laboratorio Android. Cliente de GitHub

## Datos del estudiante
- Rojas Sebastián
- PUCETEC - Desarrollo de Software

## Descripción del Proyecto
Este proyecto es una serie de laboratorios para estudiantes donde se implementará una aplicación Android que funciona como cliente de GitHub utilizando Jetpack Compose. La aplicación evoluciona progresivamente desde una UI estática hasta una integración completa con la API REST de GitHub.

---

## Laboratorio 1 — Lista de Repositorios (UI Estática)

**Objetivo:** Implementar una lista de repositorios con datos estáticos usando Jetpack Compose.

### Componentes principales
- **`RepoItem`**: Composable reutilizable que muestra el nombre de un repositorio.
- **`RepoList`**: Composable que renderiza la lista de `RepoItem` y es el que se despliega desde `MainActivity`.

### Características
- Los datos de los repositorios son estáticos (hardcoded).
- Se utiliza `Column` para mostrar la lista de forma eficiente.
- No hay conexión con ninguna API externa.

---

## Laboratorio 2 — Conexión con la API de GitHub (GET)

**Objetivo:** Conectar la interfaz con la API REST de GitHub para obtener repositorios reales.

### Características
- Integración de **Retrofit** como cliente HTTP.
- Llamada a la API de GitHub para obtener la lista de repositorios de un usuario.
- Los datos obtenidos reemplazan la lista estática del Laboratorio 1.
- Manejo básico de estados de carga y error.

---

## Laboratorio 3 — Formulario y Creación de Repositorio (POST)

**Objetivo:** Implementar un formulario para crear un nuevo repositorio en GitHub mediante la API.

### Características
- Diseño de un formulario con Composables (`TextField` / `OutlinedTextField`).
- Campos incluidos:
  - Nombre del repositorio
  - Descripción del repositorio
- Llamada **POST** a la API de GitHub para crear el repositorio.
- Manejo de la respuesta y retroalimentación al usuario.

---

## Laboratorio 4 — Actualización y Eliminación de Repositorios (PATCH / DELETE)

**Objetivo:** Implementar las operaciones de actualización y eliminación de repositorios a través de la API.

### Características
- Llamada **PATCH** para actualizar el nombre o descripción de un repositorio existente.
- Llamada **DELETE** para eliminar un repositorio.
- Confirmación antes de eliminar.
- Actualización reactiva de la lista tras cada operación.

---

## Tecnologías Utilizadas
- Kotlin
- Android SDK
- Jetpack Compose
- Material Design 3 (Material You)
- Retrofit (Laboratorios 2, 3 y 4)
- API REST de GitHub (Laboratorios 2, 3 y 4)
- Navigation Compose (opcional)


# Datos del docente
Pablo Pérez Martínez
[✉️](mailto:paperez@puce.edu.ec)

´´Comandos útiles para usar Android Studio en Mac.´´

---

# Atajos básicos

| Acción                           | Comando         |
| -------------------------------- | --------------- |
| Buscar archivos                  | `Shift + Shift` |
| Abrir clase                      | `⌘ + O`         |
| Buscar texto en proyecto         | `⌘ + Shift + F` |
| Buscar en archivo actual         | `⌘ + F`         |
| Ir a línea                       | `⌘ + L`         |
| Mostrar estructura del archivo   | `⌘ + F12`       |
| Cambiar entre archivos recientes | `⌘ + E`         |

---

# Edición de código

| Acción                   | Comando              |
| ------------------------ | -------------------- |
| Autocompletar código     | `Control + Space`    |
| Formatear código         | `⌘ + Option + L`     |
| Importar automáticamente | `Option + Enter`     |
| Duplicar línea           | `⌘ + D`              |
| Eliminar línea           | `⌘ + Backspace`      |
| Comentar línea           | `⌘ + /`              |
| Comentar bloque          | `⌘ + Option + /`     |
| Mover línea arriba       | `Option + Shift + ↑` |
| Mover línea abajo        | `Option + Shift + ↓` |

---

# Navegación rápida

| Acción                     | Comando         |
| -------------------------- | --------------- |
| Ir a definición            | `⌘ + B`         |
| Ver documentación          | `F1`            |
| Volver atrás               | `⌘ + [`         |
| Ir adelante                | `⌘ + ]`         |
| Mostrar archivos recientes | `⌘ + E`         |
| Mostrar ventanas recientes | `⌘ + Shift + E` |

---

# Refactorización

| Acción                   | Comando          |
| ------------------------ | ---------------- |
| Renombrar variable/clase | `Shift + F6`     |
| Extraer función          | `⌘ + Option + M` |
| Extraer variable         | `⌘ + Option + V` |
| Refactorizar menú        | `Control + T`    |

---

# Jetpack Compose útiles

| Acción                  | Comando                |
| ----------------------- | ---------------------- |
| Mostrar Preview         | `Split` arriba derecha |
| Actualizar Preview      | `⌘ + Shift + R`        |
| Crear composable rápido | escribir `@Composable` |
| Autoimport Compose      | `Option + Enter`       |

---

# Ejecutar la app

| Acción       | Comando       |
| ------------ | ------------- |
| Ejecutar app | `Control + R` |
| Debug app    | `Control + D` |
| Detener app  | `⌘ + F2`      |
| Abrir Logcat | `⌘ + 6`       |

---

# Terminal integrada

| Acción         | Comando        |
| -------------- | -------------- |
| Abrir terminal | `Option + F12` |

Desde ahí puedes usar comandos como:

```bash id="1fxckv"
./gradlew build
```

Compilar proyecto.

```bash id="1e3df5"
./gradlew clean
```

Limpiar build.

```bash id="mllj9v"
./gradlew installDebug
```

Instalar app en emulador/dispositivo.

---

# Comandos MUY útiles para Compose

## Crear rápidamente un composable

Escribes:

```kotlin id="32z9q3"
comp
```

y presionas `Tab`.

---

## Crear preview

Escribes:

```kotlin id="uzy4e0"
prev
```

y presionas `Tab`.

---

# Atajos más importantes para sobrevivir Android Studio

Si solo memorizas 5:

| Acción               | Comando          |
| -------------------- | ---------------- |
| Buscar TODO          | `Shift + Shift`  |
| Formatear código     | `⌘ + Option + L` |
| Arreglos automáticos | `Option + Enter` |
| Ejecutar app         | `Control + R`    |
| Buscar texto         | `⌘ + Shift + F`  |

---

# Tip extra para MacBook

Si las teclas F1–F12 no funcionan:

Ve a:

```text id="50x7qe"
System Settings → Keyboard
```

y activa:

```text id="dj8ubx"
Use F1, F2, etc. keys as standard function keys
```

---

# Recomendación importante

Activa:

```text id="rb6v6l"
Android Studio → Settings → Editor → General → Auto Import
```

y marca:

* Add imports automatically
* Optimize imports on the fly

Te evita muchos errores en Compose.
