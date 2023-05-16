package br.com.hiltonsf92.githubapp.di

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
    single<UserDatasource> { get<Retrofit>().create(UserDatasource::class.java) }
}

private val glideModule = module {
    single { Glide.with(androidContext()) }
}

private val repositoriesModule = module {
    factory<UserRepository> { UserRepositoryImpl(get()) }
}

private val usecasesModule = module {
    factory<GetUsers> { GetUsersImpl(get()) }
    factory<GetUser> { GetUserImpl(get()) }
    factory<GetRepositories> { GetRepositoriesImpl(get()) }
    factory<SearchUser> { SearchUserImpl(get()) }
}

private val viewModelsModule = module {
    viewModel { UserListViewModel(get(), get()) }
    viewModel { UserDetailViewModel(get(), get()) }
}

val appModules = listOf(
    retrofitModule,
    glideModule,
    repositoriesModule,
    usecasesModule,
    viewModelsModule
)