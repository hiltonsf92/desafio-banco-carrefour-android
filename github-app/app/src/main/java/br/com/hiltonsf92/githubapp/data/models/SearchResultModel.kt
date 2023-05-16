package br.com.hiltonsf92.githubapp.data.models

import br.com.hiltonsf92.githubapp.domain.entities.User

data class SearchResultModel(
    val items: List<UserModel>?
) {
    fun toEntity(): List<User>? = items?.map {
        User(
            id = it.id,
            avatarUrl = it.avatarUrl,
            login = it.login
        )
    }
}