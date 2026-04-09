package com.example.smartpot.data

import android.content.Context
import com.example.smartpot.data.api.MockSmartPotApi
import com.example.smartpot.data.api.RealSmartPotApi
import com.example.smartpot.data.api.SmartPotApi
import com.example.smartpot.data.api.SmartPotRetrofitService
import com.example.smartpot.data.api.TokenInterceptor
import com.example.smartpot.data.repository.SmartPotRepository
import com.example.smartpot.data.repository.TokenProvider
import com.example.smartpot.data.repository.TokenRepository
import com.example.smartpot.util.DayOfWeekSerializer
import com.example.smartpot.util.LocalTimeAsHourMinuteSerializer
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonNamingStrategy
import kotlinx.serialization.modules.SerializersModule
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.time.DayOfWeek
import java.time.LocalTime
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    private const val BASE_URL = "https://gorshok-api.onrender.com"

    @Provides
    @Singleton
    fun provideTokenRepository(@ApplicationContext context: Context): TokenRepository {
        return TokenRepository(context)
    }

    @Provides
    @Singleton
    fun provideTokenProvider(tokenRepository: TokenRepository): TokenProvider {
        return TokenProvider(tokenRepository)
    }

    @Provides
    @Singleton
    fun provideTokenInterceptor(provider: TokenProvider): TokenInterceptor {
        return TokenInterceptor(provider)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(tokenInterceptor: TokenInterceptor): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder()
            .addInterceptor(tokenInterceptor)
            .addInterceptor(logging)
            .build()
    }

    @OptIn(ExperimentalSerializationApi::class)
    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        val module = SerializersModule {
            contextual(LocalTime::class, LocalTimeAsHourMinuteSerializer)
            contextual(DayOfWeek::class, DayOfWeekSerializer)
        }

        val json = Json {
            serializersModule = module
            namingStrategy = JsonNamingStrategy.SnakeCase
            ignoreUnknownKeys = true
            encodeDefaults = false
            isLenient = true
        }

        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofitService(retrofit: Retrofit): SmartPotRetrofitService {
        return retrofit.create(SmartPotRetrofitService::class.java)
    }

    @Provides
    @Singleton
    fun provideApi(service: SmartPotRetrofitService): SmartPotApi {
        //return RealSmartPotApi(service)
        return MockSmartPotApi(500)
    }

    @Provides
    @Singleton
    fun provideRepository(api: SmartPotApi): SmartPotRepository {
        return SmartPotRepository(api)
    }
}