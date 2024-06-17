package com.capstone.hay.view.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.capstone.hay.data.model.UserModel
import com.capstone.hay.data.repository.AuthRepository

class SplashViewModel(private val authRepository: AuthRepository) : ViewModel() {

    fun getSession(): LiveData<UserModel> {
        return authRepository.getSession().asLiveData()
    }
}