package com.example.smartpot.data.mock

import com.example.smartpot.data.api.DeviceApi
import com.example.smartpot.data.api.DeviceStartRequest
import com.example.smartpot.data.api.DeviceStartResponse
import com.example.smartpot.data.api.DeviceStopRequest
import com.example.smartpot.data.api.DeviceStopResponse
import com.example.smartpot.data.api.ScheduleItem
import com.example.smartpot.data.api.ScheduleResponse
import kotlinx.coroutines.delay

class MockDeviceApi(
    private val delayMs: Long = 200L
) : DeviceApi {
    private val schedules = mutableMapOf<String, MutableList<ScheduleItem>>()

    override suspend fun postStart(request: DeviceStartRequest): DeviceStartResponse {
        delay(delayMs)
        return DeviceStartResponse(request.deviceId, true, "Mock start")
    }

    override suspend fun postStop(request: DeviceStopRequest): DeviceStopResponse {
        delay(delayMs)
        return DeviceStopResponse(request.deviceId, true, "Mock stop")
    }

    override suspend fun getSchedules(deviceId: String): ScheduleResponse {
        delay(delayMs)
        val list = schedules[deviceId]?.toList().orEmpty()
        return ScheduleResponse(true, "OK", list)
    }

    override suspend fun addSchedule(item: ScheduleItem): ScheduleResponse {
        delay(delayMs)
        schedules.getOrPut(item.deviceId) { mutableListOf() }.add(item)
        return ScheduleResponse(true, "Added", schedules[item.deviceId]!!.toList())
    }

    override suspend fun updateSchedule(item: ScheduleItem): ScheduleResponse {
        delay(delayMs)
        val list = schedules[item.deviceId] ?: return ScheduleResponse(false, "Not found")
        val idx = list.indexOfFirst { it.id == item.id }
        if (idx == -1) return ScheduleResponse(false, "Not found")
        list[idx] = item
        return ScheduleResponse(true, "Updated", list.toList())
    }

    override suspend fun deleteSchedule(item: ScheduleItem): ScheduleResponse {
        delay(delayMs)
        schedules.values.forEach { list -> list.removeAll { it.id == item.id } }
        return ScheduleResponse(true, "Deleted", schedules.values.flatten())
    }
}