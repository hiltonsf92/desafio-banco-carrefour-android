package br.com.hiltonsf92.githubapp.domain.usecases

import br.com.hiltonsf92.githubapp.domain.entities.User
import br.com.hiltonsf92.githubapp.domain.repositories.UserRepository
import javax.inject.Inject

interface SearchUser {
    suspend operator fun invoke(query: String): List<User>
}

class SearchUserImpl @Inject constructor(
    val repository: UserRepository
) : SearchUser {
    override suspend fun invoke(query: String): List<User> {
        return repository.searchUser(query)
    }
}