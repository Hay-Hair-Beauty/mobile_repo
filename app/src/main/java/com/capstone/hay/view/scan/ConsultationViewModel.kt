package com.capstone.hay.view.scan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.capstone.hay.data.model.ConsultModel

class ConsultationViewModel : ViewModel() {
    private val _consultData = MutableLiveData<ArrayList<ConsultModel>>()
    val consultData: LiveData<ArrayList<ConsultModel>> get() = _consultData

    fun loadConsultData(data: ArrayList<ConsultModel>) {
        if (_consultData.value.isNullOrEmpty()) {
            _consultData.value = data
        }
    }
}
