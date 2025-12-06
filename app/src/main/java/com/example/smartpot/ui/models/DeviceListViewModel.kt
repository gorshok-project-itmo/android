package com.example.smartpot.ui.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartpot.data.api.Device
import com.example.smartpot.data.repository.SmartPotRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class DevicesState(
    val devices: MutableMap<Int, Device> = mutableMapOf()
)

@HiltViewModel
class DeviceListViewModel @Inject constructor(private val repo: SmartPotRepository) : ViewModel() {
    private val _devices = MutableStateFlow(DevicesState())
    val devices: StateFlow<DevicesState> = _devices

    private val _schedule = MutableStateFlow(WaterScheduleState())
    val schedule: StateFlow<WaterScheduleState> = _schedule

    fun getDevices() = viewModelScope.launch {
        val resp = repo.getDevices()

        _devices.update { current ->
            current.copy(devices = resp.associateBy { it.id }.toMutableMap())
        }
    }
}