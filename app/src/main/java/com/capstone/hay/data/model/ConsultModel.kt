package com.capstone.hay.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ConsultModel(
    val name: String,
    val place: String,
    val year: Int,
    val specialist: String,
    val about: String,
    val schedule: String,
    val contact: String,
    val photo: Int
) : Parcelable
