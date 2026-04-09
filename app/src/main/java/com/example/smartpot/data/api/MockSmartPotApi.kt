package com.example.smartpot.data.api

import kotlinx.coroutines.delay
import retrofit2.Response
import kotlin.random.Random

class MockSmartPotApi(
    private val delayMs: Long = 200L
) : SmartPotApi {
    private val devices = mutableMapOf<Int, Device>()
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
        delay(delayMs)

        val id = Random.nextInt()

        val device = Device(
            id = id,
            name = request.name,
            mode = request.mode,
            intervalHours = request.intervalHours,
            durationMinutes = request.durationMinutes,
            humidityThreshold = request.humidityThreshold,
            waterLevel = request.waterLevel,
            userId = 99,
            createdAt = "",
            updatedAt = ""
        )

        devices[id] = device

        return Response.success(device)
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
        delay(delayMs)

        val id = Random.nextInt()

        val item = WateringScheduleItem(
            id = id,
            deviceId = request.wateringSchedule.deviceId,
            dayOfWeek = request.wateringSchedule.dayOfWeek,
            startTime = request.wateringSchedule.startTime,
            endTime = request.wateringSchedule.endTime,
            active = request.wateringSchedule.active,
            createdAt = "2026-01-01 00:00:00",
            updatedAt = "2026-01-01 00:00:00"
        )

        schedules[id] = item
        return Response.success(item)
    }

    override suspend fun getWateringSchedule(scheduleId: Int): Response<WateringScheduleItem> {
        val item = schedules[scheduleId]!!
        return makeResponse(item)
    }

    override suspend fun putWateringSchedule(scheduleId: Int, request: WateringScheduleRequest): Response<WateringScheduleItem> {
        delay(delayMs)

        val item = WateringScheduleItem(
            id = scheduleId,
            deviceId = request.wateringSchedule.deviceId,
            dayOfWeek = request.wateringSchedule.dayOfWeek,
            startTime = request.wateringSchedule.startTime,
            endTime = request.wateringSchedule.endTime,
            active = request.wateringSchedule.active,
            createdAt = "2026-01-01 00:00:00",
            updatedAt = "2026-01-01 00:00:00"
        )

        schedules[scheduleId] = item
        return Response.success(item)
    }

    override suspend fun deleteSchedule(scheduleId: Int): Response<Unit> {
        delay(delayMs)
        schedules.remove(scheduleId)

        return makeResponse(Unit)
    }
}