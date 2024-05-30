package org.now.sopt.mvi_pattern.service

object ServicePool {
    val imageService: ImageService by lazy { RetrofitProvider.create<ImageService>() }
}