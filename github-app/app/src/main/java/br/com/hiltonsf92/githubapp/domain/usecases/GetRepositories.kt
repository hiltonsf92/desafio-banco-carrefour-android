package br.com.hiltonsf92.githubapp.domain.usecases

import br.com.hiltonsf92.githubapp.domain.entities.Repository
import br.com.hiltonsf92.githubapp.domain.repositories.UserRepository

interface GetRepositories {
    suspend operator fun invoke(login: String): List<Repository>
}

class GetRepositoriesImpl(
    private val repository: UserRepository
) : GetRepositories {
    override suspend fun invoke(login: String): List<Repository> {
        return repository.getRepositories(login)
    }
}