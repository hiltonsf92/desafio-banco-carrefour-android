package br.com.hiltonsf92.githubapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.hiltonsf92.githubapp.domain.entities.UserData
import br.com.hiltonsf92.githubapp.domain.usecases.GetRepositories
import br.com.hiltonsf92.githubapp.domain.usecases.GetUser
import br.com.hiltonsf92.githubapp.presentation.shared.State
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserDetailViewModel(
    private val GetUser: GetUser,
    private val GetRepositories: GetRepositories
) : ViewModel() {

    private val _userStateFlow = MutableStateFlow<State<UserData>>(State.Loading)
    val userStateFlow: StateFlow<State<UserData>> get() = _userStateFlow

    fun getUserData(login: String) {
        viewModelScope.launch {
            try {
                val user = GetUser(login)
                val repos = GetRepositories(login)
                _userStateFlow.value = State.Success(UserData(user, repos))
            } catch (e: Exception) {
                _userStateFlow.value = State.Error(e)
            }
        }
    }
}