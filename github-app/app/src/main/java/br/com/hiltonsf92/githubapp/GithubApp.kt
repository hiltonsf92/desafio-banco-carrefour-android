package br.com.hiltonsf92.githubapp

import android.app.Application
import br.com.hiltonsf92.githubapp.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class GithubApp : Application() {
    override fun onCreate() {
        super.onCreate()
        setupKoin()
    }

    private fun setupKoin() {
        startKoin {
            androidLogger()
            androidContext(this@GithubApp)
            modules(appModules)
        }
    }
}