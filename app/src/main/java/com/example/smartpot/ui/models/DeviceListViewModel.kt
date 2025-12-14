package com.example.smartpot.ui.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartpot.data.api.Device
import com.example.smartpot.data.repository.SmartPotRepository
import com.example.smartpot.data.repository.TokenRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class DevicesState(
    val devices: MutableMap<Int, Device> = mutableMapOf()
)

@HiltViewModel
class DeviceListViewModel @Inject constructor(private val repo: SmartPotRepository, private val tokenRepo: TokenRepository) : ViewModel() {
    private val _devices = MutableStateFlow(DevicesState())
    val devices: StateFlow<DevicesState> = _devices

    private val _loggedOutEvent = MutableSharedFlow<Unit>(replay = 0)
    val loggedOutEvent: SharedFlow<Unit> = _loggedOutEvent

    fun getDevices() = viewModelScope.launch {
        val resp = repo.getDevices()

        _devices.update { current ->
            current.copy(devices = resp.associateBy { it.id }.toMutableMap())
        }
    }

    fun logout() {
        viewModelScope.launch {
            val resp = repo.deleteLogout()

            if (resp.status == 200) {
                tokenRepo.clearToken()
                _loggedOutEvent.emit(Unit)
            }
        }
    }
}