package br.com.hiltonsf92.githubapp.domain.usecases

import br.com.hiltonsf92.githubapp.domain.entities.User

interface GetUserByLogin {
    suspend operator fun invoke(login: String): User
}