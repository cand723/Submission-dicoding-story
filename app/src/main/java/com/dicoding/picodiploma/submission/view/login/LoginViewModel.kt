package com.dicoding.picodiploma.submission.view.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.picodiploma.submission.data.api.LoginResponse
import com.dicoding.picodiploma.submission.data.repository.UserRepository
import com.dicoding.picodiploma.submission.data.pref.UserModel
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: UserRepository) : ViewModel() {
    fun saveSession(user: UserModel) {
        viewModelScope.launch {
            repository.saveSession(user)
        }
    }

    fun login(email: String, password: String, onSuccess: (LoginResponse) -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                val response = repository.login(email, password)
                if (response.error == false) {
                    response.loginResult?.let {
                        val user = UserModel(email, it.token ?: "")
                        repository.saveSession(user)
                        onSuccess(response)
                    } ?: onError("Login result is null")
                } else {
                    onError(response.message ?: "Login failed")
                }
            } catch (e: Exception) {
                onError(e.message ?: "An error occurred")
            }
        }
    }
}