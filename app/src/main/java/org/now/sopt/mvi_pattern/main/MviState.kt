package org.now.sopt.mvi_pattern.main

import org.now.sopt.mvi_pattern.model.Image

sealed class MviState {
    data object Idle : MviState()
    data object Loading : MviState()
    data class LoadedImage(val image: Image, val count: Int) : MviState()
    data class Error(val message: String) : MviState()
}
