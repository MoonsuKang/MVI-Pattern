package org.now.sopt.mvi_pattern.main

import android.graphics.Color
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import coil.load
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.now.sopt.mvi_pattern.databinding.ActivityMviBinding
import org.now.sopt.mvi_pattern.repository.ImageRepositoryImpl

class MviActivity : AppCompatActivity() {

    private val viewModel: MviViewModel by viewModels {
        MviViewModelFactory(ImageRepositoryImpl())
    }
    private lateinit var binding: ActivityMviBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMviBinding.inflate(layoutInflater).also {
            setContentView(it.root)
            it.view = this
        }
        observeViewModel()
    }

    fun loadImage() {
        lifecycleScope.launch {
            viewModel.channel.send(MviIntent.LoadImage)
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.state.collectLatest { state ->
                when (state) {
                    is MviState.Idle -> {
                        binding.progressView.isVisible = false
                    }
                    is MviState.Loading -> {
                        binding.progressView.isVisible = true
                    }
                    is MviState.LoadedImage -> {
                        binding.progressView.isVisible = false
                        binding.imageView.run {
                            setBackgroundColor(Color.parseColor(state.image.color))
                            load(state.image.url) {
                                crossfade(300)
                            }
                        }
                        binding.imageCountTextView.text = "불러온 이미지 수 : ${state.count}"
                    }
                    is MviState.Error -> {
                        binding.progressView.isVisible = false
                        Snackbar.make(binding.root, "Error: ${state.message}", Snackbar.LENGTH_LONG)
                            .setAction("Retry") {
                                loadImage() // 다시 시도
                            }
                            .show()
                    }
                }
            }
        }
    }
}