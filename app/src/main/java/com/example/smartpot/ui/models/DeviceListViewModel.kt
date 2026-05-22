package com.example.smartpot.ui.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartpot.data.api.Device
import com.example.smartpot.data.api.DeviceRequest
import com.example.smartpot.data.repository.SmartPotRepository
import com.example.smartpot.data.repository.TokenRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.collections.associateBy

sealed class UiEvent {
    data class GetNewDevice(val device: Device?) : UiEvent()
}

data class DevicesState(
    val devices: MutableMap<String, Device> = mutableMapOf()
)

@HiltViewModel
class DeviceListViewModel @Inject constructor(private val repo: SmartPotRepository, private val tokenRepo: TokenRepository) : ViewModel() {
    private val _devices = MutableStateFlow(DevicesState())
    val devices: StateFlow<DevicesState> = _devices

    private val _newId = MutableStateFlow("")
    val newId: StateFlow<String> = _newId

    private val _events = MutableSharedFlow<UiEvent>()
    val events = _events.asSharedFlow()

    fun getDevices() = viewModelScope.launch {
        val resp = repo.getDevices()

        if (!resp.isSuccessful) {
            return@launch
        }

        _devices.update { current ->
            current.copy(devices = resp.body()!!.associateBy { it.id }.toMutableMap())
        }
    }

    fun addDevice(deviceName: String, deviceId: String) = viewModelScope.launch {
        val resp = repo.postDevices(DeviceRequest(
            id = deviceId,
            name = deviceName,
            mode = "auto",
            intervalHours = 4,
            durationMinutes = 30,
            humidityThreshold = 0.5,
            waterLevel = 100.0
        ))

        if (!resp.isSuccessful) {
            return@launch
        }

        _devices.update { current ->
            current.copy(devices = current.devices.toMutableMap().apply { put(resp.body()!!.id, resp.body()!!) })
        }
    }

    fun getNewDevice(deviceId: String) = viewModelScope.launch {
        val resp = repo.getNewDevice(deviceId)
        _events.emit(UiEvent.GetNewDevice(resp.body()))
    }

    fun onNewIdChange(v: String) { _newId.value = v }
}