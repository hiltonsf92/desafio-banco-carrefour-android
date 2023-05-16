package br.com.hiltonsf92.githubapp.domain.entities

data class User(
    val id: Int,
    val login: String,
    val name: String? = null,
    val location: String? = null,
    val url: String? = null,
    val avatarUrl: String,
    val repos: Int? = null
)