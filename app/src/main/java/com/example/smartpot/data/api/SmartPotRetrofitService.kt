package com.example.smartpot.data.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface SmartPotRetrofitService {
    @POST("signup")
    suspend fun postSignup(@Body request: AuthRequest): Response<AuthResponse>

    @POST("login")
    suspend fun postLogin(@Body request: AuthRequest): Response<AuthResponse>

    @DELETE("logout")
    suspend fun deleteLogout(): Response<LogoutResponse>

    @POST("devices")
    suspend fun postDevices(@Body request: DeviceRequest): Response<Device>

    @GET("devices")
    suspend fun getDevices(): Response<List<Device>>

    @GET("devices/{id}")
    suspend fun getDevice(@Path("id") deviceId: Int): Response<Device>

    @GET("devices/{id}/watering_status")
    suspend fun getDeviceWateringStatus(@Path("id") deviceId: Int): Response<WateringStatus>

    @POST("devices/{id}/trigger_watering")
    suspend fun postDeviceTriggerWatering(@Path("id") deviceId: Int): Response<DeviceTriggerWateringResponse>

    @GET("devices/{id}/watering_schedules")
    suspend fun getWateringSchedules(@Path("id") deviceId: Int): Response<List<WateringScheduleItem>>

    @POST("devices/{id}/watering_schedules")
    suspend fun postWateringSchedule(@Path("id") deviceId: Int, @Body request: WateringScheduleRequest): Response<WateringScheduleItem>

    @GET("watering_schedules/{id}")
    suspend fun getWateringSchedule(@Path("id") scheduleId: Int): Response<WateringScheduleItem>

    @PUT("watering_schedules/{id}")
    suspend fun putWateringSchedule(@Path("id") scheduleId: Int, @Body request: WateringScheduleRequest): Response<WateringScheduleItem>

    @DELETE("watering_schedules/{id}")
    suspend fun deleteSchedule(@Path("id") scheduleId: Int): Response<Unit>
}
