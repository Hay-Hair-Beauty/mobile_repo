package com.capstone.hay.data.api

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConfig {
    private val client: OkHttpClient
        get() {
            return OkHttpClient.Builder()
                .addInterceptor(Interceptor { chain ->
                    val original: Request = chain.request()
                    val requestBuilder: Request.Builder = original.newBuilder()
                    val request: Request = requestBuilder.build()
                    chain.proceed(request)
                })
                .build()
        }

    fun getApiService(): ApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://hay-github-actions-ubmy5cro7q-et.a.run.app/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        return retrofit.create(ApiService::class.java)
    }
}