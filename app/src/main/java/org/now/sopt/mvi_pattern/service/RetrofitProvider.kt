package org.now.sopt.mvi_pattern.service

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.now.sopt.mvi_pattern.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitProvider {
    private const val BASE_URL: String = BuildConfig.AUTH_BASE_URL

    // HTTP 로깅 인터셉터 설정
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    // API 키 인터셉터 설정
    private val apiKeyInterceptor = ApiKeyInterceptor()

    // OkHttpClient 설정
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(apiKeyInterceptor)   // API 키 인터셉터 추가
        .addInterceptor(loggingInterceptor)  // 로깅 인터셉터 추가
        .connectTimeout(30, TimeUnit.SECONDS) // 연결 타임아웃
        .readTimeout(30, TimeUnit.SECONDS)    // 읽기 타임아웃
        .writeTimeout(30, TimeUnit.SECONDS)   // 쓰기 타임아웃
        .build()

    // Retrofit 인스턴스 생성
    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create()) // JSON Converter
        .build()

    inline fun <reified T> create(): T = retrofit.create(T::class.java)
}
