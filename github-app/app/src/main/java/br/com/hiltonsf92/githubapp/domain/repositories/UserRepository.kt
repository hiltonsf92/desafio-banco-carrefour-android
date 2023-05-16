package br.com.hiltonsf92.githubapp.domain.repositories

import br.com.hiltonsf92.githubapp.domain.entities.Repository
import br.com.hiltonsf92.githubapp.domain.entities.User

interface UserRepository {
    suspend fun getUsers(): List<User>
    suspend fun getUser(login: String): User
    suspend fun getRepositories(login: String): List<Repository>
    suspend fun searchUser(query: String): List<User>
}