package br.com.hiltonsf92.githubapp.presentation.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.hiltonsf92.githubapp.domain.entities.UserData
import br.com.hiltonsf92.githubapp.domain.usecases.GetRepositories
import br.com.hiltonsf92.githubapp.domain.usecases.GetUser
import br.com.hiltonsf92.githubapp.presentation.shared.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDetailViewModel @Inject constructor(
    val GetUser: GetUser,
    val GetRepositories: GetRepositories
) : ViewModel() {

    private val _userState = MutableLiveData<State<UserData>>()
    val userState get() = _userState

    fun getUserData(login: String) {
        viewModelScope.launch {
            try {
                _userState.postValue(State.Loading)
                val user = GetUser(login)
                val repos = GetRepositories(login)
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