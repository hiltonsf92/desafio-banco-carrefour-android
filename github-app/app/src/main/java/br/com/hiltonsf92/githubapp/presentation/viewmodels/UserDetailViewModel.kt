package br.com.hiltonsf92.githubapp.presentation.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.hiltonsf92.githubapp.domain.entities.User
import br.com.hiltonsf92.githubapp.domain.usecases.GetUserByLogin
import br.com.hiltonsf92.githubapp.presentation.shared.State
import kotlinx.coroutines.launch

class UserDetailViewModel(
    private val GetUserByLogin: GetUserByLogin
) : ViewModel() {

    private val _userState = MutableLiveData<State<User>>()
    val userState get() = _userState

    fun getUserByLogin(login: String) {
        if (shouldRequestAgain(login)) {
            viewModelScope.launch {
                try {
                    _userState.postValue(State.Loading)
                    val user = GetUserByLogin(login)
                    _userState.postValue(State.Success(user))
                } catch (e: Exception) {
                    _userState.postValue(State.Error(e))
                }
            }
        }
    }

    private fun shouldRequestAgain(login: String): Boolean {
        return _userState.value?.state?.run { this as? User }.let {
            it?.login != login
        }
    }
}