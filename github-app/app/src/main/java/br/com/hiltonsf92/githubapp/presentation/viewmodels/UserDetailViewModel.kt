package br.com.hiltonsf92.githubapp.presentation.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.hiltonsf92.githubapp.domain.entities.User
import br.com.hiltonsf92.githubapp.domain.entities.UserData
import br.com.hiltonsf92.githubapp.domain.usecases.GetRepositoriesByLogin
import br.com.hiltonsf92.githubapp.domain.usecases.GetUserByLogin
import br.com.hiltonsf92.githubapp.presentation.shared.State
import kotlinx.coroutines.launch

class UserDetailViewModel(
    private val GetUserByLogin: GetUserByLogin,
    private val GetRepositoriesByLogin: GetRepositoriesByLogin
) : ViewModel() {

    private val _userState = MutableLiveData<State<UserData>>()
    val userState get() = _userState

    fun getUserByLogin(login: String) {
        viewModelScope.launch {
            try {
                _userState.postValue(State.Loading)
                val user = GetUserByLogin(login)
                val repos = GetRepositoriesByLogin(login)
                _userState.postValue(State.Success(UserData(user, repos)))
            } catch (e: Exception) {
                _userState.postValue(State.Error(e))
            }
        }
    }

    fun shouldRequestAgain(login: String): Boolean {
        return _userState.value?.state?.run { this as? UserData }.let {
            it?.user?.login != login
        }
    }
}