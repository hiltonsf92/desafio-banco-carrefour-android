package br.com.hiltonsf92.githubapp.di

import android.content.Context
import br.com.hiltonsf92.githubapp.data.datasources.UserDatasource
import br.com.hiltonsf92.githubapp.data.repositories.UserRepositoryImpl
import br.com.hiltonsf92.githubapp.domain.repositories.UserRepository
import br.com.hiltonsf92.githubapp.domain.usecases.GetRepositories
import br.com.hiltonsf92.githubapp.domain.usecases.GetRepositoriesImpl
import br.com.hiltonsf92.githubapp.domain.usecases.GetUser
import br.com.hiltonsf92.githubapp.domain.usecases.GetUserImpl
import br.com.hiltonsf92.githubapp.domain.usecases.GetUsers
import br.com.hiltonsf92.githubapp.domain.usecases.GetUsersImpl
import br.com.hiltonsf92.githubapp.domain.usecases.SearchUser
import br.com.hiltonsf92.githubapp.domain.usecases.SearchUserImpl
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    @Singleton
    @Provides
    fun provideGlide(@ApplicationContext context: Context): RequestManager {
        return Glide.with(context)
    }

    @Singleton
    @Provides
    fun provideUserDatasource(retrofit: Retrofit): UserDatasource {
        return retrofit.create(UserDatasource::class.java)
    }

    @Provides
    fun provideUserRepository(datasource: UserDatasource): UserRepository {
        return UserRepositoryImpl(datasource)
    }

    @Provides
    fun provideGetUsersUseCase(repository: UserRepository): GetUsers {
        return GetUsersImpl(repository)
    }

    @Provides
    fun provideGetUserUseCase(repository: UserRepository): GetUser {
        return GetUserImpl(repository)
    }

    @Provides
    fun provideGetRepositoriesUseCase(repository: UserRepository): GetRepositories {
        return GetRepositoriesImpl(repository)
    }

    @Provides
    fun provideSearchUserUseCase(repository: UserRepository): SearchUser {
        return SearchUserImpl(repository)
    }

    companion object {
        private const val BASE_URL = "https://api.github.com/"
    }
}