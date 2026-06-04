package ec.edu.puce.githubclient.models

import com.google.gson.annotations.SerializedName
import org.intellij.lang.annotations.Language

data class Repository(
    val id: String,
    val name:  String,
    val description: String?,
    val language: String?,
    val owner: GithubUser
// Se puede poner de las dos maneras
//    @SerializedName(value = "owner")
//    val GithubUser: GithubUser
)
