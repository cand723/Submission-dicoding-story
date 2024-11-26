package com.dicoding.picodiploma.submission.data.pref

data class UserModel(
    val email: String,
    val token: String,
    val isLogin: Boolean = false
)