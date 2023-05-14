package br.com.hiltonsf92.githubapp.data.repositories

import br.com.hiltonsf92.githubapp.data.services.GithubService
import br.com.hiltonsf92.githubapp.domain.entities.User
import br.com.hiltonsf92.githubapp.domain.exceptions.UserListException
import br.com.hiltonsf92.githubapp.domain.repositories.UserRepository

class GithubRepositoryImpl(
    private val service: GithubService
) : UserRepository {
    override suspend fun getAllUsers(): List<User> {
        val response = service.getAllUsers()
        if (response.isSuccessful) {
            val userList = response.body() ?: listOf()
            return userList.map { it.toEntity() }
        }
        throw UserListException("Unable to load users")
    }
}
