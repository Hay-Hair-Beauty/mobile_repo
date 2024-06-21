package com.capstone.hay.data.database

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.RawQuery
import androidx.sqlite.db.SupportSQLiteQuery
import com.capstone.hay.data.model.History

@Dao
interface HistoryDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertHistory(history: History)

    @RawQuery(observedEntities = [History::class])
    fun getAllHistory(query: SupportSQLiteQuery): DataSource.Factory<Int, History>
}