package com.capstone.hay.data.model

data class UserModel(
    val email: String,
    val name: String,
    val phone: String,
    val verified: Boolean = false,
    val isLogin: Boolean = false
)
