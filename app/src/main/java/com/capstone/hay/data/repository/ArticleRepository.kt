package com.capstone.hay.data.repository

import com.capstone.hay.data.api.ApiService
import com.capstone.hay.data.response.ArticleResponse
import com.capstone.hay.data.response.DataItem
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import retrofit2.HttpException

class ArticleRepository private constructor(private val apiService: ApiService){

    suspend fun getAllArticle(): Result<ArticleResponse> {
        return try {
            val response = apiService.getAllArticle()
            if (response.error === null) {
                Result.success(response)
            } else {
                Result.failure(Exception("Error: ${response.error}"))
            }
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            if (errorBody != null) {
                try {
                    val errorResponse = Gson().fromJson(errorBody, ArticleResponse::class.java)
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

    suspend fun getSearchArticle(title: String): Result<ArticleResponse> {
        return try {
            val response = apiService.searchArticle(title)
            if (response.error === null) {
                Result.success(response)
            } else {
                Result.failure(Exception("Error: ${response.error}"))
            }
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            if (errorBody != null) {
                try {
                    val errorResponse = Gson().fromJson(errorBody, ArticleResponse::class.java)
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

    suspend fun getArticleById(id: String): Result<DataItem> {
        return try {
            val response = apiService.getArticleById(id)
            if (response.message === null) {
                Result.success(response)
            } else {
                Result.failure(Exception("Error: ${response.message}"))
            }
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            if (errorBody != null) {
                try {
                    val errorResponse = Gson().fromJson(errorBody, DataItem::class.java)
                    Result.failure(Exception(errorResponse.message))
                } catch (jsonException: JsonSyntaxException) {
                    Result.failure(Exception("Unexpected error format: $errorBody"))
                }
            } else {
                Result.failure(Exception("Unknow error"))
            }
        }catch (e: Exception) {
            Result.failure(e)
        }
    }

    companion object {
        @Volatile
        private var instance: ArticleRepository? = null
        fun getInstance(
           apiService: ApiService
        ): ArticleRepository =
            instance ?: synchronized(this) {
                instance ?: ArticleRepository(apiService)
            }.also { instance = it }
    }
}