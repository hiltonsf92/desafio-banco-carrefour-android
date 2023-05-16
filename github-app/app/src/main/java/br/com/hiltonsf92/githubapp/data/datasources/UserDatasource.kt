package br.com.hiltonsf92.githubapp.data.datasources

import br.com.hiltonsf92.githubapp.data.models.RepositoryModel
import br.com.hiltonsf92.githubapp.data.models.SearchResultModel
import br.com.hiltonsf92.githubapp.data.models.UserModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UserDatasource {
    @GET("/users")
    suspend fun getUsers(): Response<List<UserModel>>

    @GET("/users/{login}")
    suspend fun getUser(@Path("login") login: String): Response<UserModel>

    @GET("/users/{login}/repos")
    suspend fun getRepositories(@Path("login") login: String, @Query("per_page") perPage: Int): Response<List<RepositoryModel>>

    @GET("/search/users")
    suspend fun searchUser(@Query("q") query: String, @Query("per_page") perPage: Int): Response<SearchResultModel>
}