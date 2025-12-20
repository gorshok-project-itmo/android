package com.example.smartpot.data.api

import retrofit2.Response

class RealSmartPotApi(
    private val service: SmartPotRetrofitService
) : SmartPotApi {

    override suspend fun postSignup(request: AuthRequest): Response<AuthResponse> =
        service.postSignup(request)

    override suspend fun postLogin(request: AuthRequest): Response<AuthResponse> =
        service.postLogin(request)

    override suspend fun deleteLogout(): Response<LogoutResponse> =
        service.deleteLogout()

    override suspend fun postDevices(request: DeviceRequest): Response<Device> =
        service.postDevices(request)

    override suspend fun getDevices(): Response<List<Device>> =
        service.getDevices()

    override suspend fun getDevice(deviceId: Int): Response<Device> =
        service.getDevice(deviceId)

    override suspend fun getDeviceWateringStatus(deviceId: Int): Response<WateringStatus> =
        service.getDeviceWateringStatus(deviceId)

    override suspend fun postDeviceTriggerWatering(deviceId: Int): Response<DeviceTriggerWateringResponse> =
        service.postDeviceTriggerWatering(deviceId)

    override suspend fun getWateringSchedules(deviceId: Int): Response<List<WateringScheduleItem>> =
        service.getWateringSchedules(deviceId)

    override suspend fun postWateringSchedule(request: WateringScheduleRequest): Response<WateringScheduleItem> =
        service.postWateringSchedule(request.wateringSchedule.deviceId, request)

    override suspend fun getWateringSchedule(scheduleId: Int): Response<WateringScheduleItem> =
        service.getWateringSchedule(scheduleId)

    override suspend fun putWateringSchedule(scheduleId: Int, request: WateringScheduleRequest): Response<WateringScheduleItem> =
        service.putWateringSchedule(scheduleId, request)

    override suspend fun deleteSchedule(scheduleId: Int): Response<Unit> =
        service.deleteSchedule(scheduleId)
}
