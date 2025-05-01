package com.teksiak.overlayservice

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

class TimerViewModel : ViewModel() {

    private var timerJob: Job? = null

    var elapsedTime by mutableStateOf(Duration.ZERO)
    var isPaused by mutableStateOf(false)

    fun startTimer() {
        if (timerJob != null) return

        isPaused = false

        timerJob = viewModelScope.launch {
            while (true) {
                elapsedTime += 1.seconds
                kotlinx.coroutines.delay(1000)
            }
        }
    }

    fun pauseTimer() {
        timerJob?.cancel()
        timerJob = null
        isPaused = true
    }
}