package com.example.smartpot.ui.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartpot.data.api.AuthRequest
import com.example.smartpot.data.api.Device
import com.example.smartpot.data.api.User
import com.example.smartpot.data.api.WateringScheduleItem
import com.example.smartpot.data.api.WateringScheduleRequest
import com.example.smartpot.data.repository.DeviceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class DevicesState(
    val devices: MutableMap<Int, Device> = mutableMapOf()
)

data class WaterScheduleState(
    val schedule: MutableMap<Int, WateringScheduleItem> = mutableMapOf()
)

data class UserState(
    val user: User
)

class DeviceViewModel(private val repo: DeviceRepository) : ViewModel() {
    private val _devices = MutableStateFlow(DevicesState())
    val devices: StateFlow<DevicesState> = _devices

    private val _user = MutableStateFlow(UserState(User(1, "test@test.com")))
    val user: StateFlow<UserState> = _user

    private val _schedule = MutableStateFlow(WaterScheduleState())
    val schedule: StateFlow<WaterScheduleState> = _schedule

    fun postSignup(request: AuthRequest) = viewModelScope.launch {
        val resp = repo.postSignup(request)
    }

    fun postLogin(request: AuthRequest) = viewModelScope.launch {
        val resp = repo.postLogin(request)
    }

    fun deleteLogout() = viewModelScope.launch {
        val resp = repo.deleteLogout()
    }

    fun getDevices() = viewModelScope.launch {
        val resp = repo.getDevices()

        _devices.update { current ->
            current.copy(devices = resp.associateBy { it.id }.toMutableMap())
        }
    }

    fun getDevice(deviceId: Int) = viewModelScope.launch {
        val resp = repo.getDevice(deviceId)

        _devices.update { current ->
            current.copy(current.devices.toMutableMap().also { it.put(resp.id, resp) })
        }
    }

    fun getDeviceWateringStatus(deviceId: Int) = viewModelScope.launch {
        val resp = repo.getDeviceWateringStatus(deviceId)
    }

    fun postDeviceTriggerWatering(deviceId: Int) = viewModelScope.launch {
        val resp = repo.postDeviceTriggerWatering(deviceId)
    }

    fun getWateringSchedules(deviceId: Int) = viewModelScope.launch {
        val resp = repo.getWateringSchedules(deviceId)

        _schedule.update { current ->
            current.copy(resp.associateBy { it.id }.toMutableMap())
        }
    }

    fun postWateringSchedule(request: WateringScheduleRequest) = viewModelScope.launch {
        val resp = repo.postWateringSchedule(request)

        _schedule.update { current ->
            val new = current.schedule.toMutableMap().also { it.put(resp.id, resp) }
            current.copy(new)
        }
    }

    fun getWateringSchedule(id: Int) = viewModelScope.launch {
        val resp = repo.getWateringSchedule(id)

        _schedule.update { current ->
            current.copy(current.schedule.toMutableMap().also { it.put(resp.id, resp) })
        }
    }

    fun putWateringSchedule(item: WateringScheduleItem) = viewModelScope.launch {
        val resp = repo.putWateringSchedule(item)

        _schedule.update { current ->
            current.copy(current.schedule.toMutableMap().also { it.put(resp.id, resp) })
        }
    }

    fun deleteSchedule(id: Int) = viewModelScope.launch {
        val resp = repo.deleteSchedule(id)

        _schedule.update { current ->
            current.copy(current.schedule.toMutableMap().also { it.remove(id) })
        }
    }
}