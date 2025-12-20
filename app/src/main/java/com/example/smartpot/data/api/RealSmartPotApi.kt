package com.example.smartpot.data.api

class RealSmartPotApi(
    private val service: SmartPotRetrofitService
) : SmartPotApi {

    override suspend fun postSignup(request: AuthRequest): AuthResponse =
        service.postSignup(request)

    override suspend fun postLogin(request: AuthRequest): AuthResponse =
        service.postLogin(request)

    override suspend fun deleteLogout(): LogoutResponse =
        service.deleteLogout()

    override suspend fun postDevices(request: DeviceRequest): Device =
        service.postDevices(request)

    override suspend fun getDevices(): List<Device> =
        service.getDevices()

    override suspend fun getDevice(deviceId: Int): Device? =
        try { service.getDevice(deviceId) } catch (e: retrofit2.HttpException) {
            if (e.code() == 404) null else throw e
        }

    override suspend fun getDeviceWateringStatus(deviceId: Int): WateringStatus =
        service.getDeviceWateringStatus(deviceId)

    override suspend fun postDeviceTriggerWatering(deviceId: Int): DeviceTriggerWateringResponse =
        service.postDeviceTriggerWatering(deviceId)

    override suspend fun getWateringSchedules(deviceId: Int): List<WateringScheduleItem> =
        service.getWateringSchedules(deviceId)

    override suspend fun postWateringSchedule(request: WateringScheduleRequest): WateringScheduleItem =
        service.postWateringSchedule(request.wateringSchedule.deviceId, request)

    override suspend fun getWateringSchedule(scheduleId: Int): WateringScheduleItem =
        service.getWateringSchedule(scheduleId)

    override suspend fun putWateringSchedule(scheduleId: Int, request: WateringScheduleRequest): WateringScheduleItem =
        service.putWateringSchedule(scheduleId, request)

    override suspend fun deleteSchedule(scheduleId: Int) =
        service.deleteSchedule(scheduleId)
}
