package org.now.sopt.mvi_pattern.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.now.sopt.mvi_pattern.model.Image
import org.now.sopt.mvi_pattern.service.ServicePool

class ImageRepositoryImpl(
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ImageRepository {

    override suspend fun getRandomImage(): Image = withContext(dispatcher) {
        val response = ServicePool.imageService.getRandomImageSuspend()
        Image(response.urls.regular, response.color)
    }
}