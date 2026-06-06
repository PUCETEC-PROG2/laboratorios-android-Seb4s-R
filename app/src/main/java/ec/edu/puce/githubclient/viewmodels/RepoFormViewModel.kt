package ec.edu.puce.githubclient.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ec.edu.puce.githubclient.models.Repository
import ec.edu.puce.githubclient.models.RepositoryPayload
import ec.edu.puce.githubclient.services.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import retrofit2.Response

/**
 * ViewModel que gestiona la lógica de creación, actualización y eliminación.
 * Corregido para manejar correctamente respuestas HTTP 204 (No Content).
 */
class RepoFormViewModel : ViewModel() {
    private val TAG = "RepoFormViewModel"

    // Estado de carga para mostrar indicadores visuales
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    // Mensaje de error para mostrar retroalimentación al usuario
    private val _errorMsg = MutableStateFlow<String?>(null)
    val errorMsg: StateFlow<String?> = _errorMsg.asStateFlow()

    // Estado de éxito para notificar a la UI el fin de una operación exitosa
    private val _isSuccess = MutableStateFlow(false)
    val isSuccess: StateFlow<Boolean> = _isSuccess.asStateFlow()

    // Repositorio que se está editando actualmente (null si es creación)
    private val _editingRepo = MutableStateFlow<Repository?>(null)
    val editingRepo: StateFlow<Repository?> = _editingRepo.asStateFlow()

    /**
     * Establece el repositorio que se desea editar.
     * @param repo El objeto Repository a editar o null para modo creación.
     */
    fun setEditingRepo(repo: Repository?) {
        _editingRepo.value = repo
    }

    /**
     * Actualiza la descripción.
     */
    fun updateRepo(owner: String, oldName: String, description: String) {
        executeNetworkAction("PATCH") {
            Log.d(TAG, "PATCH -> $owner/$oldName")
            val repoBody = RepositoryPayload(oldName, description)
            RetrofitClient.apiServices.updateRepository(owner, oldName, repoBody)
        }
    }

    /**
     * Elimina un repositorio. Maneja explícitamente el objeto Response.
     */
    fun deleteRepo(owner: String, repoName: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMsg.value = null
            _isSuccess.value = false
            try {
                Log.d(TAG, "DELETE -> $owner/$repoName")
                val response = RetrofitClient.apiServices.deleteRepository(owner, repoName)
                
                if (response.isSuccessful) {
                    Log.d(TAG, "DELETE exitoso (Código ${response.code()})")
                    _isSuccess.value = true
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e(TAG, "Error DELETE [${response.code()}]: $errorBody")
                    handleHttpError(response.code())
                }
            } catch (e: Exception) {
                Log.e(TAG, "Excepción en DELETE", e)
                _errorMsg.value = "Error de conexión: ${e.localizedMessage}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    /**
     * Crea un repositorio nuevo.
     */
    fun createRepo(name: String, description: String) {
        executeNetworkAction("POST") {
            Log.d(TAG, "POST -> $name")
            val repoBody = RepositoryPayload(name, description)
            RetrofitClient.apiServices.createRepository(repoBody)
        }
    }

    /**
     * Ejecutor genérico de peticiones con logs y manejo de excepciones HttpException.
     */
    private fun executeNetworkAction(actionLabel: String, action: suspend () -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMsg.value = null
            _isSuccess.value = false
            try {
                action()
                Log.d(TAG, "$actionLabel completado")
                _isSuccess.value = true
            } catch (e: HttpException) {
                Log.e(TAG, "HttpException en $actionLabel: ${e.code()}")
                handleHttpError(e.code())
            } catch (e: Exception) {
                Log.e(TAG, "Error en $actionLabel", e)
                _errorMsg.value = "Error: ${e.localizedMessage}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun handleHttpError(code: Int) {
        _errorMsg.value = when (code) {
            403 -> "Permiso denegado. Tu token requiere el scope 'delete_repo'."
            404 -> "Repositorio no encontrado."
            422 -> "Error de validación en GitHub."
            else -> "Error del servidor GitHub ($code)."
        }
    }

    fun resetSuccess() { _isSuccess.value = false }
    fun resetError() { _errorMsg.value = null }
}
