package com.capstone.hay.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.capstone.hay.data.database.HistoryDao
import com.capstone.hay.data.database.QueryHelper
import com.capstone.hay.data.model.History

class HistoryRepository(private val historyDao: HistoryDao) {
    fun getAllHistory(start: Long?, end: Long?, email: String): LiveData<PagedList<History>> {
        val query = QueryHelper.getAllHistoryBetween(start, end, email)
        val history = historyDao.getAllHistory(query)

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(true)
            .setInitialLoadSizeHint(8)
            .setPageSize(8)
            .build()

        return LivePagedListBuilder(history, config).build()
    }

    suspend fun insertHistory(history: History) {
        historyDao.insertHistory(history)
    }
}