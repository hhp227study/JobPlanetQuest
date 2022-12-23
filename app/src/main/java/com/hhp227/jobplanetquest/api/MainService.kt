package com.hhp227.jobplanetquest.api

import com.hhp227.jobplanetquest.model.CellResponse
import com.hhp227.jobplanetquest.model.RecruitResponse
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import kotlinx.serialization.json.Json
import retrofit2.http.GET

interface MainService {
    @GET("test_data_recruit_items.json")
    suspend fun getTestDataRecruitItems(): RecruitResponse

    @GET("test_data_cell_items.json")
    suspend fun getTestDataCellItems(): CellResponse

    companion object {
        private val Json = Json {
            isLenient = true
            ignoreUnknownKeys = true
            coerceInputValues = true
            classDiscriminator = "#class"
        }

        private const val BASE_URL = "https://jpassets.jobplanet.co.kr/mobile-config/"

        fun create(): MainService {
            val logger = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }
            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
                .build()
                .create(MainService::class.java)
        }
    }
}