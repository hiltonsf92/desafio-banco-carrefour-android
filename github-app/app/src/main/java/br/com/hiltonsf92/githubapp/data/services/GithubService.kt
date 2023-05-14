package br.com.hiltonsf92.githubapp.data.services

import br.com.hiltonsf92.githubapp.data.models.GithubUserModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubService {
    @GET("/users")
    suspend fun getAllUsers(): Response<List<GithubUserModel>>

    @GET("/users/{login}")
    suspend fun getUserByLogin(@Path("login") login: String): Response<GithubUserModel>
}