package com.capstone.hay.view.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.hay.data.model.UserModel
import com.capstone.hay.data.repository.AuthRepository
import com.capstone.hay.data.response.LoginResponse
import kotlinx.coroutines.launch

class LoginViewModel(private val authRepository: AuthRepository) : ViewModel() {

    private val _loginResult = MutableLiveData<Result<LoginResponse>>()
    val loginResult: LiveData<Result<LoginResponse>> = _loginResult

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = authRepository.login(email, password)
            _isLoading.value = false
            result.getOrNull()?.let { response ->
                try {
                    saveSession(
                        UserModel(
                            response.token,
                            response.userId,
                            response.userData.email,
                            response.userData.name,
                            response.userData.phone,
                            response.userData.verified
                        )
                    )
                    _loginResult.value = Result.success(response)
                } catch (e: Exception) {
                    _loginResult.value = Result.failure(e)
                }
            } ?: run {
                _loginResult.value = result
            }
        }
    }

    fun saveSession(user: UserModel) {
        viewModelScope.launch {
            authRepository.saveSession(user)
        }
    }
}