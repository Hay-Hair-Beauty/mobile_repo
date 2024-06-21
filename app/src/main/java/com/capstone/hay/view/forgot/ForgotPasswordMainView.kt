package com.capstone.hay.view.forgot

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.hay.data.repository.AuthRepository
import com.capstone.hay.data.response.ResetPasswordResponse
import com.capstone.hay.utils.Event
import kotlinx.coroutines.launch

class ForgotPasswordMainView(private val authRepository: AuthRepository) : ViewModel()  {
    private val _forgotPasswordResult = MutableLiveData<Result<ResetPasswordResponse>>()
    val forgotPasswordResult: LiveData<Result<ResetPasswordResponse>> = _forgotPasswordResult

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _snackbarText = MutableLiveData<Event<String>>()
    val snackbarText: LiveData<Event<String>> = _snackbarText

    fun forgotPassword(email: String) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = authRepository.resetPassword(email)
            _isLoading.value = false
            if (result.isSuccess) {
                _forgotPasswordResult.value = result
            } else {
                _snackbarText.value = Event(result.exceptionOrNull()?.message ?: "Internal server error")
            }
        }
    }
}