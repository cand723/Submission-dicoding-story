package com.dicoding.picodiploma.submission.data.repository

import android.util.Log
import com.dicoding.picodiploma.submission.data.api.ApiService
import com.dicoding.picodiploma.submission.data.api.LoginResponse
import com.dicoding.picodiploma.submission.data.api.RegisterResponse
import com.dicoding.picodiploma.submission.data.pref.UserModel
import com.dicoding.picodiploma.submission.data.pref.UserPreference
import kotlinx.coroutines.flow.Flow

class UserRepository private constructor(
    private val userPreference: UserPreference,
    private val apiService: ApiService
) {

    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    suspend fun logout() {
        userPreference.logout()
    }

    suspend fun registerUser(name: String, email: String, password: String): RegisterResponse {
        val response = apiService.register(name, email, password)
        Log.d("UserRepository", "Register Response: $response")
        return response
    }
    suspend fun login(email: String, password: String): LoginResponse {
        val response = apiService.login(email, password)
        Log.d("UserRepository", "Login Response: $response")
        return response
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            userPreference: UserPreference,
            apiService: ApiService
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(userPreference, apiService)
            }.also { instance = it }
    }
}