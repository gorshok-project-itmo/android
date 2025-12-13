package com.example.smartpot.ui.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartpot.data.api.Device
import com.example.smartpot.data.api.WateringScheduleItem
import com.example.smartpot.data.api.WateringScheduleRequest
import com.example.smartpot.data.repository.SmartPotRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalTime
import javax.inject.Inject

data class DeviceState(
    val device: Device?
)

data class WaterScheduleState(
    val schedule: MutableMap<Int, WateringScheduleItem> = mutableMapOf()
)

@HiltViewModel
class DeviceViewModel @Inject constructor(private val repo: SmartPotRepository) : ViewModel() {
    private val _device = MutableStateFlow(DeviceState(null))
    val device: StateFlow<DeviceState> = _device

    private val _schedule = MutableStateFlow(WaterScheduleState())
    val schedule: StateFlow<WaterScheduleState> = _schedule

    fun getDevice(deviceId: Int) = viewModelScope.launch {
        val resp = repo.getDevice(deviceId)

        _device.value = DeviceState(resp)
    }

    fun putDevice(device: Device) = viewModelScope.launch {
        val resp = repo.putDevice(device)

        _device.value = DeviceState(resp)
    }

    fun setHumidityThreshold(value: Int) {
        putDevice(_device.value.device!!.copy(humidityThreshold = (value.toDouble()) / 100))
    }

    fun setDeviceName(value: String) {
        putDevice(_device.value.device!!.copy(name = value))
    }

    fun getDeviceWateringStatus() = viewModelScope.launch {
        val resp = repo.getDeviceWateringStatus(device.value.device!!.id)
    }

    fun postDeviceTriggerWatering() = viewModelScope.launch {
        val resp = repo.postDeviceTriggerWatering(device.value.device!!.id)
    }

    fun getWateringSchedules(deviceId: Int) = viewModelScope.launch {
        val resp = repo.getWateringSchedules(deviceId)

        _schedule.update { current ->
            current.copy(resp.associateBy { it.id }.toMutableMap())
        }
    }

    fun postWateringSchedule(startTime: LocalTime, endTime: LocalTime, dayOfWeek: DayOfWeek) = viewModelScope.launch {
        val device = _device.value.device ?: return@launch

        val resp = repo.postWateringSchedule(WateringScheduleRequest(
            deviceId = device.id,
            startTime = startTime,
            endTime = endTime,
            dayOfWeek = dayOfWeek,
            active = true
        ))

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