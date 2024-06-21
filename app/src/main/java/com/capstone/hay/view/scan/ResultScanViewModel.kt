package com.capstone.hay.view.scan

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.hay.data.repository.HairIssueRepository
import com.capstone.hay.data.response.DataItemResponse
import com.capstone.hay.data.response.HairIssueResponse
import com.capstone.hay.utils.Event
import kotlinx.coroutines.launch
import okhttp3.RequestBody

class ResultScanViewModel(private val hairIssueRepository: HairIssueRepository) : ViewModel() {

    private val _hairIssuesResult = MutableLiveData<Result<HairIssueResponse>>()
    val hairIssuesResult: LiveData<Result<HairIssueResponse>> = _hairIssuesResult

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _snackbarText = MutableLiveData<Event<String>>()
    val snackbarText: LiveData<Event<String>> = _snackbarText

    fun getHairIssue(hairIssue: String) {

        viewModelScope.launch {
            _isLoading.value = true
            val result = hairIssueRepository.getHairIssue(hairIssue)
            if (result.isSuccess) {
                _hairIssuesResult.value = result
                _isLoading.value = false
            } else {
                _snackbarText.value = Event(result.exceptionOrNull()?.message ?: "Internal server error")
                _isLoading.value = false
            }
        }

    }
}