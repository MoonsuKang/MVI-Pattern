package org.now.sopt.mvi_pattern.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.viewModelScope
import coil.compose.rememberAsyncImagePainter
import kotlinx.coroutines.launch
import org.now.sopt.mvi_pattern.model.Image

@Composable
fun MviScreen(viewModel: MviViewModel) {
    val state by viewModel.state.collectAsState()

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        val (imageView, countTextView, button) = createRefs()

        when (state) {
            is MviState.Idle -> IdleState(modifier = Modifier.constrainAs(imageView) {
                top.linkTo(parent.top)
                bottom.linkTo(button.top)
                height = Dimension.fillToConstraints
            })
            is MviState.Loading -> LoadingState(modifier = Modifier.constrainAs(imageView) {
                top.linkTo(parent.top)
                bottom.linkTo(button.top)
                height = Dimension.fillToConstraints
            })
            is MviState.LoadedImage -> LoadedImageState(
                image = (state as MviState.LoadedImage).image,
                modifier = Modifier.constrainAs(imageView) {
                    top.linkTo(parent.top)
                    bottom.linkTo(button.top)
                    height = Dimension.fillToConstraints
                }
            )
            is MviState.Error -> ErrorState((state as MviState.Error).message, modifier = Modifier.constrainAs(imageView) {
                top.linkTo(parent.top)
                bottom.linkTo(button.top)
                height = Dimension.fillToConstraints
            }) {
                viewModel.viewModelScope.launch {
                    viewModel.channel.send(MviIntent.LoadImage)
                }
            }
        }

        Text(
            text = if (state is MviState.LoadedImage) "불러온 이미지 수 : ${(state as MviState.LoadedImage).count}" else "",
            modifier = Modifier.constrainAs(countTextView) {
                bottom.linkTo(button.top, margin = 16.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )

        Button(
            onClick = {
                viewModel.viewModelScope.launch {
                    viewModel.channel.send(MviIntent.LoadImage)
                }
            },
            modifier = Modifier.constrainAs(button) {
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        ) {
            Text("이미지 불러오기")
        }
    }
}

@Composable
fun IdleState(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Idle")
    }
}

@Composable
fun LoadingState(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
fun LoadedImageState(image: Image, modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        val painter = rememberAsyncImagePainter(image.url)
        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(android.graphics.Color.parseColor(image.color)))
        )
    }
}

@Composable
fun ErrorState(message: String, modifier: Modifier = Modifier, onRetry: () -> Unit) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Snackbar(
            action = {
                TextButton(onClick = onRetry) {
                    Text("Retry")
                }
            }
        ) {
            Text("Error: $message")
        }
    }
}