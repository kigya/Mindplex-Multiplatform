package dev.kigya.mindplex

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.kigya.mindplex.core.presentation.theme.MindplexTheme

@Composable
fun App() {
    MindplexTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
        ) {
            Text(
                text = Greeting().greet(),
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.scrim,
            )
        }
    }
}
