package com.capstone.hay.data.database

import androidx.sqlite.db.SimpleSQLiteQuery
import java.lang.StringBuilder

object QueryHelper {
    fun getAllHistoryBetween(start: Long?, end: Long?, email: String): SimpleSQLiteQuery {
        val query = StringBuilder().append("SELECT * FROM history WHERE email = ?")

        if (start != null && end != null) {
            query.append(" AND createdBy BETWEEN ? AND ? ORDER BY createdBy DESC")
            return SimpleSQLiteQuery(query.toString(), arrayOf(email, start, end))
        }

        return SimpleSQLiteQuery(query.toString(), arrayOf(email))
    }
}