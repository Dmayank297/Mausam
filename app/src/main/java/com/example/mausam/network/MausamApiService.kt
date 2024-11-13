package com.example.mausam.network

import com.example.mausam.data.MausamData
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://api.weatherapi.com"

//adding a retrofit builder
private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface MausamApiService {
    @GET("/v1/current.json")
    suspend fun getMausam(
        @Query("key") apiKey: String,
        @Query("q") city: String
    ): MausamData
}

object MausamApi {
    val retrofitService: MausamApiService by lazy {
        retrofit.create(MausamApiService::class.java)
    }
}