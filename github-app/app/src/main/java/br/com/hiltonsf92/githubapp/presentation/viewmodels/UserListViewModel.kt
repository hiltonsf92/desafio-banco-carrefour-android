package br.com.hiltonsf92.githubapp.presentation.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.hiltonsf92.githubapp.domain.entities.User
import br.com.hiltonsf92.githubapp.domain.usecases.GetAllUsers
import br.com.hiltonsf92.githubapp.presentation.shared.State
import kotlinx.coroutines.launch

class UserListViewModel(
    private val GetAllUsers: GetAllUsers
) : ViewModel() {

    private val _userListState = MutableLiveData<State<List<User>>>()
    val userListState get() = _userListState

    private val shouldRequestAgain get() = _userListState.value == null || _userListState.value?.hasError() == true

    fun getAllUsers() {
        if (shouldRequestAgain) {
            viewModelScope.launch {
                try {
                    _userListState.postValue(State.Loading)
                    val userList = GetAllUsers()
                    _userListState.postValue(State.Success(userList))
                } catch (e: Exception) {
                    _userListState.postValue(State.Error(e))
                }
            }
        }
    }
}