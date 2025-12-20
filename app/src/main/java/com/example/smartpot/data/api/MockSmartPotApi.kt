package com.example.smartpot.data.api

import kotlinx.coroutines.delay
import retrofit2.Response

class MockSmartPotApi(
    private val delayMs: Long = 200L
) : SmartPotApi {
    private val devices = mutableMapOf<Int, Device>(1 to Device(
        id = 1,
        name = "MyDevice1",
        mode = "schedule",
        intervalHours = 5,
        durationMinutes = 30,
        humidityThreshold = 0.3,
        waterLevel = 1.0,
        userId = 99,
        createdAt = "",
        updatedAt = ""
    ))
    private val schedules = mutableMapOf<Int, WateringScheduleItem>()
    private var token: String? = null

    fun <T> makeResponse(data: T): Response<T> =
        Response.success(data)

    override suspend fun postSignup(request: AuthRequest): Response<AuthResponse> {
        delay(delayMs)
        val token = "1234567890"

        val resp = AuthResponse(
            AuthStatus(
                200,
                "success",
                token,
                UserData(
                    1,
                    "test@test.com"
                )
            )
        )

        this.token = token

        return makeResponse(resp)
    }

    override suspend fun postLogin(request: AuthRequest): Response<AuthResponse> {
        delay(delayMs)
        val token = "1234567890"

        val resp = AuthResponse(
            AuthStatus(
                200,
                "success",
                token,
                UserData(
                    1,
                    "test@test.com"
                )
            )
        )

        this.token = token

        return makeResponse(resp)
    }

    override suspend fun deleteLogout(): Response<LogoutResponse> {
        delay(delayMs)

        val resp = LogoutResponse(
            200,
            "success"
        )

        return makeResponse(resp)
    }

    override suspend fun postDevices(request: DeviceRequest): Response<Device> {
        TODO("Not yet implemented")
    }

    override suspend fun getDevices(): Response<List<Device>> {
        delay(delayMs)
        val resp = this.devices.values.toList()

        return makeResponse(resp)
    }

    override suspend fun getDevice(deviceId: Int): Response<Device> {
        delay(delayMs)
        val resp = this.devices[deviceId]

        return makeResponse(resp!!)
    }

    override suspend fun getDeviceWateringStatus(deviceId: Int): Response<WateringStatus> {
        delay(delayMs)
        val device = devices[deviceId]!!

        val resp = WateringStatus(
            device.name,
            device.mode,
            device.waterLevel,
            device.humidityThreshold,
            false
        )

        return makeResponse(resp)
    }

    override suspend fun postDeviceTriggerWatering(deviceId: Int): Response<DeviceTriggerWateringResponse> {
        TODO()
//        delay(delayMs)
//        return DeviceTriggerWateringResponse(
//            "Mock start",
//            10,
//            Timestamp.from(Instant.now()) as Timestamp
//        )
    }

    override suspend fun getWateringSchedules(deviceId: Int): Response<List<WateringScheduleItem>> {
        delay(delayMs)

        val resp = schedules.filter { it.value.deviceId == deviceId }.toList().map {
            it.second
        }

        return makeResponse(resp)
    }

    override suspend fun postWateringSchedule(request: WateringScheduleRequest): Response<WateringScheduleItem> {
        TODO()

//        delay(delayMs)
//
//        val id = Random.Default.nextInt()
//
//        val item = WateringScheduleItem(
//            id = id,
//            deviceId = request.wateringSchedule.deviceId,
//            dayOfWeek = request.wateringSchedule.dayOfWeek,
//            startTime = request.wateringSchedule.startTime,
//            endTime = request.wateringSchedule.endTime,
//            active = request.wateringSchedule.active,
//            createdAt = TODO(),
//            updatedAt = TODO()
//        )
//
//        schedules[id] = item
//        return item
    }

    override suspend fun getWateringSchedule(scheduleId: Int): Response<WateringScheduleItem> {
        val item = schedules[scheduleId]!!
        return makeResponse(item)
    }

    override suspend fun putWateringSchedule(scheduleId: Int, request: WateringScheduleRequest): Response<WateringScheduleItem> {
        TODO()
    }

    override suspend fun deleteSchedule(scheduleId: Int): Response<Unit> {
        delay(delayMs)
        schedules.remove(scheduleId)

        return makeResponse(Unit)
    }
}