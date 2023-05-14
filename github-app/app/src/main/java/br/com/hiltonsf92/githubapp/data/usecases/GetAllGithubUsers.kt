package br.com.hiltonsf92.githubapp.data.usecases

import br.com.hiltonsf92.githubapp.domain.entities.User
import br.com.hiltonsf92.githubapp.domain.repositories.UserRepository
import br.com.hiltonsf92.githubapp.domain.usecases.GetAllUsers

class GetAllGithubUsers(
    private val repository: UserRepository
) : GetAllUsers {
    override suspend fun invoke(): List<User> {
        return repository.getAllUsers()
    }
}