package br.com.hiltonsf92.githubapp.domain.usecases

import br.com.hiltonsf92.githubapp.domain.entities.User

interface GetAllUsers {
    suspend operator fun invoke(): List<User>
}