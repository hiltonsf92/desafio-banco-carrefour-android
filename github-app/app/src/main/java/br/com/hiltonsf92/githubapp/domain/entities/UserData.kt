package br.com.hiltonsf92.githubapp.domain.entities

data class UserData(
    val user: User,
    val repositories: List<Repository>
)