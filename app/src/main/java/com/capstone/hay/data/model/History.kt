package com.capstone.hay.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class History(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    val email: String,
    val hairIssue: String,
    val descHairIssue: String,
    val causedBy: String,
    val treatment: String,
    val photo: String,
    val createdBy: Long
)
