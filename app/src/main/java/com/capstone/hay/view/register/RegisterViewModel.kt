package com.capstone.hay.view.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.hay.data.repository.AuthRepository
import com.capstone.hay.data.response.RegisterResponse
import com.capstone.hay.utils.Event
import kotlinx.coroutines.launch

class RegisterViewModel(private val authRepository: AuthRepository) : ViewModel() {

    private val _registerResult = MutableLiveData<Result<RegisterResponse>>()
    val registerResult: LiveData<Result<RegisterResponse>> = _registerResult

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _snackbarText = MutableLiveData<Event<String>>()
    val snackbarText: LiveData<Event<String>> = _snackbarText

    fun register(name: String, phone: String, email: String, password: String, confirmPassword: String) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = authRepository.register(name, phone, email, password, confirmPassword)
            _isLoading.value = false
            if (result.isSuccess) {
                _registerResult.value = result
            } else {
                _snackbarText.value = Event(result.exceptionOrNull()?.message ?: "Internal server error")
            }
        }
    }
}