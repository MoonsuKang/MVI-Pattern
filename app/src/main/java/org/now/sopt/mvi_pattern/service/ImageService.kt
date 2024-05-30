package org.now.sopt.mvi_pattern.service

import org.now.sopt.mvi_pattern.model.ImageResponse
import retrofit2.http.GET

interface ImageService {
    @GET("photos/random")
    suspend fun getRandomImageSuspend(): ImageResponse
}