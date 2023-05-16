package br.com.hiltonsf92.githubapp.domain.usecases

import br.com.hiltonsf92.githubapp.domain.entities.User
import br.com.hiltonsf92.githubapp.domain.repositories.UserRepository

interface GetUsers {
    suspend operator fun invoke(): List<User>
}

class GetUsersImpl(
    private val repository: UserRepository
) : GetUsers {
    override suspend fun invoke(): List<User> {
        return repository.getUsers()
    }
}