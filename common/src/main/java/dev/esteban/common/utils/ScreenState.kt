package dev.esteban.common.utils

sealed class ScreenState {
    object Loading : ScreenState()
    object Success : ScreenState()
    object Error : ScreenState()
    object Empty : ScreenState()
}
