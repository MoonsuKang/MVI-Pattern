package org.now.sopt.mvi_pattern.main

sealed class MviIntent {
    data object LoadImage : MviIntent()
}