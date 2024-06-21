package com.capstone.hay.data.di

import android.content.Context
import com.capstone.hay.data.api.ApiConfig
import com.capstone.hay.data.database.HistoryDao
import com.capstone.hay.data.database.HistoryDatabase
import com.capstone.hay.data.pref.UserPreference
import com.capstone.hay.data.pref.dataStore
import com.capstone.hay.data.repository.ArticleRepository
import com.capstone.hay.data.repository.AuthRepository
import com.capstone.hay.data.repository.HairIssueRepository
import com.capstone.hay.data.repository.HistoryRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {
    fun provideAuthRepository(context: Context): AuthRepository {
        return AuthRepository.getInstance(UserPreference.getInstance(context.dataStore), ApiConfig.getApiService())
    }

    fun provideArticleRepository(context: Context): ArticleRepository {
        return ArticleRepository.getInstance(ApiConfig.getApiService())
    }

    fun provideHairIssueRepository(context: Context): HairIssueRepository {
        return HairIssueRepository.getInstance(ApiConfig.getApiService())
    }

    fun provideHistoryRepository(context: Context): HistoryRepository {
        val database = HistoryDatabase.getDatabase(context)
        return HistoryRepository(database.historyDao())
    }
}