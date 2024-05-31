package org.now.sopt.mvi_pattern.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.now.sopt.mvi_pattern.repository.ImageRepositoryImpl

class MviActivity : ComponentActivity() {

    private val viewModel: MviViewModel by viewModels {
        MviViewModelFactory(ImageRepositoryImpl())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MviApp(viewModel)
        }
    }
}

@Composable
fun MviApp(viewModel: MviViewModel) {
    MaterialTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            MviScreen(viewModel)
        }
    }
}