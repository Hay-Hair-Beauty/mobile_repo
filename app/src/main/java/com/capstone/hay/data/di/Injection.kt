package com.capstone.hay.data.di

import android.content.Context
import com.capstone.hay.data.api.ApiConfig
import com.capstone.hay.data.pref.UserPreference
import com.capstone.hay.data.pref.dataStore
import com.capstone.hay.data.repository.AuthRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {
    fun provideAuthRepository(context: Context): AuthRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val apiService = ApiConfig.getApiService()
        return AuthRepository.getInstance(pref, apiService)
    }
}