package br.com.hiltonsf92.githubapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.hiltonsf92.githubapp.domain.entities.User
import br.com.hiltonsf92.githubapp.domain.usecases.GetUsers
import br.com.hiltonsf92.githubapp.domain.usecases.SearchUser
import br.com.hiltonsf92.githubapp.presentation.shared.State
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserListViewModel(
    private val GetUsers: GetUsers,
    private val SearchUser: SearchUser
) : ViewModel() {

    private val _userListStateFlow = MutableStateFlow<State<List<User>>>(State.Success(emptyList()))
    val userListStateFlow: StateFlow<State<List<User>>> get() = _userListStateFlow

    fun getAllUsers() {
        viewModelScope.launch {
            try {
                _userListStateFlow.value = State.Loading
                val userList = GetUsers()
                _userListStateFlow.value = State.Success(userList)
            } catch (e: Exception) {
                _userListStateFlow.value = State.Error(e)
            }
        }
    }

    fun searchUser(login: String) {
        viewModelScope.launch {
            try {
                _userListStateFlow.value = State.Loading
                val userList = SearchUser(login)
                _userListStateFlow.value = State.Success(userList)
            } catch (e: Exception) {
                _userListStateFlow.value = State.Error(e)
            }
        }
    }

    fun shouldRequestAgain(): Boolean {
        return _userListStateFlow.value.state?.run { this as? List<*> }.let {
            it?.isEmpty() ?: true
        }
    }
}