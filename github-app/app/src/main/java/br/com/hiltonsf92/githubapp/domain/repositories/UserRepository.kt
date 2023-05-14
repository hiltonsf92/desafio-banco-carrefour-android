package br.com.hiltonsf92.githubapp.domain.repositories

import br.com.hiltonsf92.githubapp.domain.entities.User

interface UserRepository {
    suspend fun getAllUsers(): List<User>
}