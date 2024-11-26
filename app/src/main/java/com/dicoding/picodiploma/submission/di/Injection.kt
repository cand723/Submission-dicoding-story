package com.dicoding.picodiploma.submission.di

import android.content.Context
import com.dicoding.picodiploma.submission.data.api.ApiConfig
import com.dicoding.picodiploma.submission.data.repository.UserRepository
import com.dicoding.picodiploma.submission.data.pref.UserPreference
import com.dicoding.picodiploma.submission.data.pref.dataStore

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val apiService = ApiConfig.getApiService()
        return UserRepository.getInstance(pref, apiService)
    }
}