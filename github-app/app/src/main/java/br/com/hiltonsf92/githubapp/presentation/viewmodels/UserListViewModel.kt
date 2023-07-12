package br.com.hiltonsf92.githubapp.presentation.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.hiltonsf92.githubapp.domain.entities.User
import br.com.hiltonsf92.githubapp.domain.usecases.GetUsers
import br.com.hiltonsf92.githubapp.domain.usecases.SearchUser
import br.com.hiltonsf92.githubapp.presentation.shared.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(
    val GetUsers: GetUsers,
    val SearchUser: SearchUser
) : ViewModel() {

    private val _userListState = MutableLiveData<State<List<User>>>()
    val userListState get() = _userListState

    private var _lastQuery: String? = null
    val lastQuery: String? = _lastQuery

    fun getAllUsers() {
        viewModelScope.launch {
            try {
                _userListState.postValue(State.Loading)
                val users = GetUsers()
                _userListState.postValue(State.Success(users))
            } catch (e: Exception) {
                _userListState.postValue(State.Error(e))
            }
        }
    }

    fun searchUser(query: String) {
        _lastQuery = query
        viewModelScope.launch {
            try {
                _userListState.postValue(State.Loading)
                val users = SearchUser(query)
                _userListState.postValue(State.Success(users))
            } catch (e: Exception) {
                _userListState.postValue(State.Error(e))
            }
        }
    }

    fun shouldRequestAgain() =
        _userListState.value == null || _userListState.value?.hasError() == true

    fun isInvalidQuery(query: String): Boolean {
        return query.isEmpty() || _lastQuery?.lowercase() == query.lowercase()
    }
}