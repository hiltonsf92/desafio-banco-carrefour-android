package br.com.hiltonsf92.githubapp.data.usecases

import br.com.hiltonsf92.githubapp.domain.entities.User
import br.com.hiltonsf92.githubapp.domain.repositories.UserRepository
import br.com.hiltonsf92.githubapp.domain.usecases.GetUserByLogin

class GetGithubUserByLogin(
    private val repository: UserRepository
) : GetUserByLogin {
    override suspend fun invoke(login: String): User {
        return repository.getUserByLogin(login)
    }
}