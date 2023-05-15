package br.com.hiltonsf92.githubapp.data.usecases

import br.com.hiltonsf92.githubapp.domain.entities.Repository
import br.com.hiltonsf92.githubapp.domain.repositories.UserRepository
import br.com.hiltonsf92.githubapp.domain.usecases.GetRepositoriesByLogin

class GetGithubRepositoriesByLogin(
    private val repository: UserRepository
) : GetRepositoriesByLogin {
    override suspend fun invoke(login: String): List<Repository> {
        return repository.getRepositoriesByLogin(login)
    }
}