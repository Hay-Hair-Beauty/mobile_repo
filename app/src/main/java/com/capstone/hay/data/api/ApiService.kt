package com.capstone.hay.data.api

import com.capstone.hay.data.response.ArticleResponse
import com.capstone.hay.data.response.DataItem
import com.capstone.hay.data.response.LoginResponse
import com.capstone.hay.data.response.RegisterResponse
import com.capstone.hay.data.response.VerifyResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @FormUrlEncoded
    @POST("auth/login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse

    @FormUrlEncoded
    @POST("auth/register")
    suspend fun register(
        @Field("name") name: String,
        @Field("phone") phone: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("confirmPassword") confirmPassword: String
    ): RegisterResponse

    @FormUrlEncoded
    @POST("auth/verify")
    suspend fun verify(
        @Field("email") email: String,
        @Field("code") code: String
    ): VerifyResponse

    @GET("news/articles")
    suspend fun getAllArticle(): ArticleResponse

    @GET("news/article/{id}")
    suspend fun getArticleById(@Path("id") id: String): DataItem
}