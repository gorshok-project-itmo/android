package com.example.smartpot.data.repository

import com.example.smartpot.data.api.DeviceApi
import com.example.smartpot.data.api.DeviceStartRequest
import com.example.smartpot.data.api.DeviceStopRequest
import com.example.smartpot.data.api.ScheduleItem

class DeviceRepository(private val api: DeviceApi) {
    suspend fun sendStart(deviceId: String) = api.postStart(DeviceStartRequest(deviceId))
    suspend fun sendStop(deviceId: String) = api.postStop(DeviceStopRequest(deviceId))

    suspend fun fetchSchedules(deviceId: String) = api.getSchedules(deviceId)
    suspend fun addSchedule(item: ScheduleItem) = api.addSchedule(item)
    suspend fun updateSchedule(item: ScheduleItem) = api.updateSchedule(item)
    suspend fun deleteSchedule(item: ScheduleItem) = api.deleteSchedule(item)


}