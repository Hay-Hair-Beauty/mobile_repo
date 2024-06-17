package com.capstone.hay.data.repository

import com.capstone.hay.data.api.ApiService
import com.capstone.hay.data.model.UserModel
import com.capstone.hay.data.pref.UserPreference
import com.capstone.hay.data.response.LoginResponse
import com.capstone.hay.data.response.RegisterResponse
import com.capstone.hay.data.response.VerifyResponse
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException

class AuthRepository private constructor(
    private val userPreference: UserPreference, private val apiService: ApiService
) {

    suspend fun login(email: String, password: String): Result<LoginResponse> {
        return try {
            val response = apiService.login(email, password)
            if (response.message !== null && response.error === null) {
                Result.success(response)
            } else {
                Result.failure(Exception("Error: ${response.error}"))
            }
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            if (errorBody != null) {
                try {
                    val errorResponse = Gson().fromJson(errorBody, RegisterResponse::class.java)
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

    suspend fun register(name: String, phone: String, email: String, password: String, confirmPassword: String): Result<RegisterResponse> {
        return try {
            val response = apiService.register(name, phone, email, password, confirmPassword)
            if (response.message !== null && response.error === null) {
                Result.success(response)
            } else {
                Result.failure(Exception("Error: ${response.error}"))
            }
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            if (errorBody != null) {
                try {
                    val errorResponse = Gson().fromJson(errorBody, RegisterResponse::class.java)
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

    suspend fun verifyAccount(email: String, code: String): Result<VerifyResponse> {
        return try {
            val response = apiService.verify(email, code)
            if (response.message !== null && response.error === null) {
                Result.success(response)
            } else {
                Result.failure(Exception("Error: ${response.error}"))
            }
        } catch (e: HttpException){
            val errorBody = e.response()?.errorBody()?.string()
            if (errorBody != null) {
                try {
                    val errorResponse = Gson().fromJson(errorBody, RegisterResponse::class.java)
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

    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    suspend fun logout() {
        userPreference.logout()
    }

    companion object {
        @Volatile
        private var instance: AuthRepository? = null
        fun getInstance(
            userPreference: UserPreference, apiService: ApiService
        ): AuthRepository =
            instance ?: synchronized(this) {
                instance ?: AuthRepository(userPreference, apiService)
            }.also { instance = it }
    }
}