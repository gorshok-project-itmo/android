package com.example.smartpot.ui.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartpot.data.repository.TokenRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class LaunchState {
    object Loading: LaunchState()
    object SignedOut: LaunchState()
    data class SignedIn(val token: String): LaunchState()
}

@HiltViewModel
class LaunchViewModel @Inject constructor(private val tokenRepo: TokenRepository) : ViewModel() {
    private val _state = MutableStateFlow<LaunchState>(LaunchState.Loading)
    val state: StateFlow<LaunchState> = _state

    init { checkToken() }

    private fun checkToken() {
        viewModelScope.launch {
            val token = tokenRepo.tokenFlow.first()
            if (!token.isNullOrBlank()) _state.value = LaunchState.SignedIn(token)
            else _state.value = LaunchState.SignedOut
        }
    }

    fun signOut() {
        viewModelScope.launch {
            tokenRepo.clearToken()
            _state.value = LaunchState.SignedOut
        }
    }

    fun onSignedIn(token: String) {
        viewModelScope.launch {
            tokenRepo.saveToken(token)
            _state.value = LaunchState.SignedIn(token)
        }
    }
}
