package com.example.smartpot.data

import android.content.Context
import com.example.smartpot.data.api.MockSmartPotApi
import com.example.smartpot.data.api.SmartPotApi
import com.example.smartpot.data.repository.SmartPotRepository
import com.example.smartpot.data.repository.TokenRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Provides
    @Singleton
    fun provideApi(): SmartPotApi {
        return MockSmartPotApi()
    }

    @Provides
    @Singleton
    fun provideRepository(api: SmartPotApi): SmartPotRepository {
        return SmartPotRepository(api)
    }

    @Provides
    @Singleton
    fun provideTokenRepository(@ApplicationContext context: Context): TokenRepository {
        return TokenRepository(context)
    }
}