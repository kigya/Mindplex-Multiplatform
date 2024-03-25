package com.kigya.mindplex.shared.core.presentation.theme

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun App() {
    MindplexTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
        ) {
            Text(
                text = "Hello Android",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.scrim,
            )
        }
    }
}
