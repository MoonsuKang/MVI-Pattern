package org.now.sopt.mvi_pattern.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.now.sopt.mvi_pattern.repository.ImageRepository

class MviViewModel(private val imageRepository: ImageRepository) : ViewModel() {

    val channel = Channel<MviIntent>()

    private val _state = MutableStateFlow<MviState>(MviState.Idle)
    val state: StateFlow<MviState> get() = _state

    private var count = 0

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            channel.consumeAsFlow().collectLatest { intent ->
                when (intent) {
                    MviIntent.LoadImage -> loadImage()
                }
            }
        }
    }

    private fun loadImage() {
        viewModelScope.launch {
            _state.value = MviState.Loading
            try {
                val image = withContext(Dispatchers.IO) {
                    imageRepository.getRandomImage()
                }
                count++
                _state.value = MviState.LoadedImage(image, count)
            } catch (e: Exception) {
                _state.value = MviState.Error(e.message ?: "Unknown Error")
            }
        }
    }
}
