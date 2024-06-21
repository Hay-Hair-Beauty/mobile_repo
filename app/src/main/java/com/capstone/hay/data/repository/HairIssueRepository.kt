package com.capstone.hay.data.repository

import android.util.Log
import com.capstone.hay.data.api.ApiService
import com.capstone.hay.data.response.DataItemResponse
import com.capstone.hay.data.response.HairIssueResponse
import com.capstone.hay.data.response.RegisterResponse
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import retrofit2.HttpException

class HairIssueRepository private constructor(private val apiService: ApiService){

    suspend fun getHairIssue(label: String): Result<HairIssueResponse> {
        return try {
            val response = apiService.getHairIssueData(label.trim())
            if (response.error === null) {
                Result.success(response)
            } else {
                Result.failure(Exception("Error: ${response.error}"))
            }
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            if (errorBody != null) {
                try {
                    val errorResponse = Gson().fromJson(errorBody, HairIssueResponse::class.java)
                    Result.failure(Exception(errorResponse.error))
                } catch (jsonException: JsonSyntaxException) {
                    Result.failure(Exception("Unexpected error format: $errorBody"))
                }
            } else {
                Result.failure(Exception("Unknown error"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    companion object {
        @Volatile
        private var instance: HairIssueRepository? = null
        fun getInstance(
            apiService: ApiService
        ): HairIssueRepository =
            instance ?: synchronized(this) {
                instance ?: HairIssueRepository(apiService)
            }.also { instance = it }
    }
}