package com.capstone.hay.view.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagedList
import com.capstone.hay.data.model.History
import com.capstone.hay.data.repository.HistoryRepository
import kotlinx.coroutines.launch

class HistoryViewModel(private val historyRepository: HistoryRepository) : ViewModel() {

    fun getAllHistory(start: Long?, end: Long?, email: String): LiveData<PagedList<History>> {
        return historyRepository.getAllHistory(start, end, email)
    }

    fun insertData(history: History) {
        viewModelScope.launch {
            historyRepository.insertHistory(history)
        }
    }
}