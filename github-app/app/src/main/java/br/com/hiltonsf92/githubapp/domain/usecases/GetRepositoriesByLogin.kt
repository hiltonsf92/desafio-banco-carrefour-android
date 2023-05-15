package br.com.hiltonsf92.githubapp.domain.usecases

import br.com.hiltonsf92.githubapp.domain.entities.Repository

interface GetRepositoriesByLogin {
    suspend operator fun invoke(login: String): List<Repository>
}