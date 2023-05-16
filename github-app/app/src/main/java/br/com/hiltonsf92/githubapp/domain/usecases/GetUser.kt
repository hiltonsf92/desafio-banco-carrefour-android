package br.com.hiltonsf92.githubapp.domain.usecases

import br.com.hiltonsf92.githubapp.domain.entities.User
import br.com.hiltonsf92.githubapp.domain.repositories.UserRepository

interface GetUser {
    suspend operator fun invoke(login: String): User
}

class GetUserImpl(
    private val repository: UserRepository
) : GetUser {
    override suspend fun invoke(login: String): User {
        return repository.getUser(login)
    }
}