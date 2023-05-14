package br.com.hiltonsf92.githubapp.presentation.shared

import java.lang.Exception

sealed class State<out T : Any> {
    object Loading : State<Nothing>()
    data class Success<out T : Any>(val data: T) : State<T>()
    data class Error(val exception: Exception) : State<Nothing>()

    val state: Any?
        get() = when (this) {
            is Success -> data
            is Error -> exception
            else -> null
        }

    fun handle(
        loading: () -> Unit,
        success: (data: T) -> Unit,
        error: (exception: Exception) -> Unit
    ) {
        when (this) {
            is Loading -> loading()
            is Success -> success(data)
            is Error -> error(exception)
        }
    }

    fun hasError() = this is Error
}