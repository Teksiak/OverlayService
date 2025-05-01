package com.teksiak.overlayservice

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

@Composable
fun TimerOverlayContent(
    elapsedTime: Duration,
    isPaused: Boolean,
    onResume: () -> Unit,
    onPause: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(
                color = Color.Black,
                shape = RoundedCornerShape(24.dp)
            )
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(
            12.dp,
            alignment = Alignment.CenterVertically
        ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "${if(elapsedTime.inWholeMinutes < 10) "0" else ""}${elapsedTime.inWholeMinutes}" +
                    ":${if(elapsedTime.inWholeSeconds % 60 < 10) "0" else ""}${elapsedTime.inWholeSeconds % 60}",
            style = MaterialTheme.typography.headlineLarge,
            color = if(isPaused) Color.Gray else Color.White,
        )
        Row(
            modifier = Modifier
                .height(IntrinsicSize.Max),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            TextButton(
                onClick = onDelete
            ) {
                Text(
                    text = "Delete",
                    color = Color.Red
                )
            }
            VerticalDivider()
            if (isPaused) {
                TextButton(
                    onClick = onResume
                ) {
                    Text(text = "Resume")
                }
            } else {
                TextButton(
                    onClick = onPause
                ) {
                    Text(text = "Pause")
                }
            }
        }
    }
}

@Preview
@Composable
private fun TimerOverlayContentPreview() {
    MaterialTheme {
        TimerOverlayContent(
            elapsedTime = 50.minutes + 23.seconds,
            isPaused = false,
            onResume = {},
            onPause = {},
            onDelete = {}
        )
    }
}