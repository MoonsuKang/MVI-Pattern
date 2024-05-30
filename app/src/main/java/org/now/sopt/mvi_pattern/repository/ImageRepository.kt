package org.now.sopt.mvi_pattern.repository

import org.now.sopt.mvi_pattern.model.Image

interface ImageRepository {
    suspend fun getRandomImage() : Image
}