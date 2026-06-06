package ec.edu.puce.githubclient.services

import ec.edu.puce.githubclient.models.Repository
import ec.edu.puce.githubclient.models.RepositoryPayload
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Interface que define los endpoints de la API de GitHub.
 * Utiliza Retrofit para realizar las peticiones HTTP.
 */
interface ApiServices {
    
    /**
     * Obtiene la lista de repositorios del usuario autenticado.
     *
     * @param created Ordenar por fecha de creación.
     * @param direction Dirección del orden (descendente por defecto).
     * @param affiliation Tipo de relación con el repositorio (dueño).
     * @param perPage Cantidad de resultados por página.
     * @param t Parámetro de caché para forzar la actualización.
     */
    @GET("/user/repos")
    suspend fun getRepositories (
        @Query("sort") created : String = "created",
        @Query("direction") direction : String = "desc",
        @Query("affiliation") affiliation : String = "owner",
        @Query("per_page") perPage : Int = 100,
        @Query("t") t : String = "${System.currentTimeMillis()}"
    ) : List<Repository>

    /**
     * Crea un nuevo repositorio para el usuario autenticado.
     *
     * @param repository Datos del nuevo repositorio (nombre y descripción).
     */
    @POST("/user/repos")
    suspend fun createRepository (
        @Body repository: RepositoryPayload
    ) : Repository

    /**
     * Actualiza un repositorio existente.
     *
     * @param owner Nombre de usuario del dueño del repositorio.
     * @param repo Nombre actual del repositorio.
     * @param repository Datos a actualizar (nombre y descripción).
     */
    @PATCH("/repos/{owner}/{repo}")
    suspend fun updateRepository(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Body repository: RepositoryPayload
    ): Repository

    /**
     * Elimina un repositorio existente.
     * Se usa Response<Unit> para manejar correctamente el HTTP 204 No Content.
     */
    @DELETE("/repos/{owner}/{repo}")
    suspend fun deleteRepository(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): Response<Unit>

}
