package br.com.hiltonsf92.githubapp.data.repositories

import br.com.hiltonsf92.githubapp.data.datasources.UserDatasource
import br.com.hiltonsf92.githubapp.domain.entities.Repository
import br.com.hiltonsf92.githubapp.domain.entities.User
import br.com.hiltonsf92.githubapp.domain.exceptions.RepositoriesException
import br.com.hiltonsf92.githubapp.domain.exceptions.SearchUserException
import br.com.hiltonsf92.githubapp.domain.exceptions.UserException
import br.com.hiltonsf92.githubapp.domain.exceptions.UsersException
import br.com.hiltonsf92.githubapp.domain.repositories.UserRepository

class UserRepositoryImpl(
    private val datasource: UserDatasource
) : UserRepository {
    override suspend fun getUsers(): List<User> {
        val response = datasource.getUsers()
        if (response.isSuccessful) {
            val users = response.body() ?: listOf()
            return users.map { it.toEntity() }
        }
        throw UsersException(USERS_EXCEPTION)
    }

    override suspend fun getUser(login: String): User {
        val response = datasource.getUser(login)
        if (response.isSuccessful) {
            return response.body()?.toEntity()
                ?: throw UserException(USER_EXCEPTION)
        }
        throw UserException(USER_EXCEPTION)
    }

    override suspend fun getRepositories(login: String): List<Repository> {
        val response = datasource.getRepositories(login, 5)
        if (response.isSuccessful) {
            val repos = response.body() ?: listOf()
            return repos.map { it.toEntity() }
        }
        throw RepositoriesException(REPOSITORIES_EXCEPTION)
    }

    override suspend fun searchUser(query: String): List<User> {
        val response = datasource.searchUser(query, 5)
        if (response.isSuccessful) {
            val result = response.body()?.toEntity() ?: listOf()
            if (result.isEmpty()) throw SearchUserException(SEARCH_USER_EXCEPTION)
            return result
        }
        throw SearchUserException(SEARCH_USER_EXCEPTION)
    }

    companion object {
        private const val USERS_EXCEPTION = "Unable to load users"
        private const val USER_EXCEPTION = "Unable to load this user's data"
        private const val REPOSITORIES_EXCEPTION = "Unable to load repos"
        private const val SEARCH_USER_EXCEPTION = "No results for this search"
    }
}
