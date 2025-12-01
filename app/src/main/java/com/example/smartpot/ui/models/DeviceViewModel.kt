package com.example.smartpot.ui.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartpot.data.api.ScheduleItem
import com.example.smartpot.data.repository.DeviceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class UiState(
    val devices: MutableMap<String, DeviceState> = mutableMapOf("1" to DeviceState("1", "MyDevice1", false, listOf()))
)

data class DeviceState(
    val id: String,
    val title: String,
    val isRunning: Boolean,
    val schedule: List<ScheduleItem>
)

class DeviceViewModel(private val repo: DeviceRepository) : ViewModel() {
    private val _ui = MutableStateFlow(UiState())
    val ui: StateFlow<UiState> = _ui

    fun start(deviceId: String) = viewModelScope.launch {
        val resp = repo.sendStart(deviceId)
        if (!resp.success) {
            return@launch
        }

        _ui.update { current ->
            val old = current.devices[deviceId] ?: return@update current
            val updated = old.copy(isRunning = true)
            val newMap = current.devices.toMutableMap().also { it[deviceId] = updated }
            current.copy(devices = newMap)
        }
    }

    fun stop(deviceId: String) = viewModelScope.launch {
        val resp = repo.sendStop(deviceId)
        if (!resp.success) {
            return@launch
        }

        _ui.update { current ->
            val old = current.devices[deviceId] ?: return@update current
            val updated = old.copy(isRunning = false)
            val newMap = current.devices.toMutableMap().also { it[deviceId] = updated }
            current.copy(devices = newMap)
        }
    }

    fun loadSchedules(deviceId: String) = viewModelScope.launch {
        val resp = repo.fetchSchedules(deviceId)
        _ui.update { current ->
            val old = current.devices[deviceId] ?: return@update current
            val updated = old.copy(schedule = resp.schedule)
            val newMap = current.devices.toMutableMap().also { it[deviceId] = updated }
            current.copy(devices = newMap)
        }
    }

    fun addSchedule(item: ScheduleItem) = viewModelScope.launch {
        val resp = repo.addSchedule(item)
        val deviceId = item.deviceId
        _ui.update { current ->
            val old = current.devices[deviceId] ?: return@update current
            val updated = old.copy(schedule = resp.schedule)
            val newMap = current.devices.toMutableMap().also { it[deviceId] = updated }
            current.copy(devices = newMap)
        }
    }

    fun updateSchedule(item: ScheduleItem) = viewModelScope.launch {
        val resp = repo.updateSchedule(item)
        val deviceId = item.deviceId
        _ui.update { current ->
            val old = current.devices[deviceId] ?: return@update current
            val updated = old.copy(schedule = resp.schedule)
            val newMap = current.devices.toMutableMap().also { it[deviceId] = updated }
            current.copy(devices = newMap)
        }
    }

    fun deleteSchedule(item: ScheduleItem) = viewModelScope.launch {
        val resp = repo.deleteSchedule(item)
        val deviceId = item.deviceId
        _ui.update { current ->
            val old = current.devices[deviceId] ?: return@update current
            val updated = old.copy(schedule = resp.schedule)
            val newMap = current.devices.toMutableMap().also { it[deviceId] = updated }
            current.copy(devices = newMap)
        }
    }
}