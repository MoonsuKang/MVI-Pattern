package org.now.sopt.mvi_pattern.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.now.sopt.mvi_pattern.repository.ImageRepository

class MviViewModelFactory(private val imageRepository: ImageRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MviViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MviViewModel(imageRepository) as T
        }
        throw IllegalArgumentException("알 수 없는 ViewModel 클래스")
    }
}