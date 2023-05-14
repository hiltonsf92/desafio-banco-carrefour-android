package br.com.hiltonsf92.githubapp.di

import br.com.hiltonsf92.githubapp.data.repositories.GithubRepositoryImpl
import br.com.hiltonsf92.githubapp.data.services.GithubService
import br.com.hiltonsf92.githubapp.data.usecases.GetAllGithubUsers
import br.com.hiltonsf92.githubapp.data.usecases.GetGithubUserByLogin
import br.com.hiltonsf92.githubapp.domain.usecases.GetUserByLogin
import br.com.hiltonsf92.githubapp.domain.repositories.UserRepository
import br.com.hiltonsf92.githubapp.domain.usecases.GetAllUsers
import br.com.hiltonsf92.githubapp.presentation.viewmodels.UserDetailViewModel
import br.com.hiltonsf92.githubapp.presentation.viewmodels.UserListViewModel
import com.bumptech.glide.Glide
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://api.github.com/"

private val retrofitModule = module {
    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    single<GithubService> { get<Retrofit>().create(GithubService::class.java) }
}

private val glideModule = module {
    single { Glide.with(androidContext()) }
}

private val repositoriesModule = module {
    factory<UserRepository> { GithubRepositoryImpl(get()) }
}

private val usecasesModule = module {
    factory<GetAllUsers> { GetAllGithubUsers(get()) }
    factory<GetUserByLogin> { GetGithubUserByLogin(get()) }
}

private val viewModelsModule = module {
    viewModel { UserListViewModel(get()) }
    viewModel { UserDetailViewModel(get()) }
}

val appModules = listOf(
    retrofitModule,
    glideModule,
    repositoriesModule,
    usecasesModule,
    viewModelsModule
)