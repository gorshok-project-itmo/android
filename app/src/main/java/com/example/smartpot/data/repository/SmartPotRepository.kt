package com.example.smartpot.data.repository

import com.example.smartpot.data.api.AuthRequest
import com.example.smartpot.data.api.SmartPotApi
import com.example.smartpot.data.api.WateringScheduleItem
import com.example.smartpot.data.api.WateringScheduleRequest

class SmartPotRepository(private val api: SmartPotApi) {
    suspend fun postSignup(request: AuthRequest) = api.postSignup(request)
    suspend fun postLogin(request: AuthRequest) = api.postLogin(request)
    suspend fun deleteLogout() = api.deleteLogout()
    suspend fun getDevices() = api.getDevices()
    suspend fun getDevice(deviceId: Int) = api.getDevice(deviceId)
    suspend fun getDeviceWateringStatus(deviceId: Int) = api.getDeviceWateringStatus(deviceId)
    suspend fun postDeviceTriggerWatering(deviceId: Int) = api.postDeviceTriggerWatering(deviceId)


    suspend fun getWateringSchedules(deviceId: Int) = api.getWateringSchedules(deviceId)
    suspend fun postWateringSchedule(request: WateringScheduleRequest) = api.postWateringSchedule(request)
    suspend fun getWateringSchedule(scheduleId: Int) = api.getWateringSchedule(scheduleId)
    suspend fun putWateringSchedule(scheduleId: Int, request: WateringScheduleRequest) = api.putWateringSchedule(scheduleId, request)
    suspend fun deleteSchedule(scheduleId: Int) = api.deleteSchedule(scheduleId)
}