package br.com.hiltonsf92.githubapp.data.models

import br.com.hiltonsf92.githubapp.domain.entities.User
import com.google.gson.annotations.SerializedName

data class GithubUserModel(
    val id: Int,
    val login: String,
    @SerializedName("avatar_url")
    val avatarUrl: String
) {
    fun toEntity(): User = User(id = id, login = login, avatarUrl = avatarUrl)
}
