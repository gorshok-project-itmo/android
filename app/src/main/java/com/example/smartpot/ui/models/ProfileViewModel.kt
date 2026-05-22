package com.example.smartpot.ui.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartpot.data.api.Device
import com.example.smartpot.data.api.DeviceRequest
import com.example.smartpot.data.repository.SmartPotRepository
import com.example.smartpot.data.repository.TokenProvider
import com.example.smartpot.data.repository.TokenRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.collections.associateBy

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repo: SmartPotRepository,
    private val tokenRepo: TokenRepository,
    private val tokenProvider: TokenProvider
) : ViewModel() {
    private val _loggedOutEvent = MutableSharedFlow<Unit>(replay = 0)
    val loggedOutEvent: SharedFlow<Unit> = _loggedOutEvent

    val email: StateFlow<String?> = tokenRepo.emailFlow
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = tokenProvider.getEmail()
        )

    fun logout() {
        viewModelScope.launch {
            val resp = repo.deleteLogout()

            if (!resp.isSuccessful) {
                return@launch
            }

            tokenRepo.clearToken()

            _loggedOutEvent.emit(Unit)
        }
    }
}