package com.capstone.hay.view.verification

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.hay.data.repository.AuthRepository
import com.capstone.hay.data.response.VerifyResponse
import com.capstone.hay.utils.Event
import kotlinx.coroutines.launch

class VerificationViewModel(private val authRepository: AuthRepository) : ViewModel() {

    private val _verifyResult  = MutableLiveData<Result<VerifyResponse>>()
    val verifyResult: LiveData<Result<VerifyResponse>> = _verifyResult

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _snackbarText = MutableLiveData<Event<String>>()
    val snackbarText: LiveData<Event<String>> = _snackbarText

    fun verificationAccount(email: String, code: String) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = authRepository.verifyAccount(email, code)
            _isLoading.value = false
            if (result.isSuccess) {
                _verifyResult.value = result
            } else {
                _snackbarText.value = Event(result.exceptionOrNull()?.message ?: "Internal server error")
            }
        }
    }
}