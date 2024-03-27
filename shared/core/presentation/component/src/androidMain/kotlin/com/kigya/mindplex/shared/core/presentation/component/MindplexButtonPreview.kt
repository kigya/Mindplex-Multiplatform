package com.kigya.mindplex.shared.core.presentation.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.kigya.mindplex.shared.core.presentation.theme.MindplexTheme
import com.kigya.mindplex.shared.core.presentation.theme.spacing.spacing

@Composable
@Preview
private fun BlackButtonPreview() {
    MindplexTheme {
        MaterialTheme.spacing
        MidplexButton(
            isLoading = true,
            modifier = Modifier.fillMaxWidth(),
            buttonLabel = "BlackButton",
            onClick = {},
        )
    }
}

@Composable
@Preview
private fun BlackButtonWithStartIconPreview() {
    MindplexTheme {
        MidplexButton(
            modifier = Modifier.fillMaxWidth(),
            buttonLabel = "BlackButton",
            startIcon = {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                )
            },
            onClick = {},
        )
    }
}

@Composable
@Preview
private fun BlackButtonDisabledPreview() {
    MindplexTheme {
        MidplexButton(
            modifier = Modifier.fillMaxWidth(),
            isEnabled = false,
            buttonLabel = "BlackButton",
            onClick = {},
        )
    }
}

@Composable
@Preview
private fun BlackButtonWithEndIconPreview() {
    MindplexTheme {
        MidplexButton(
            modifier = Modifier.fillMaxWidth(),
            buttonLabel = "BlackButton",
            endIcon = {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                )
            },
            onClick = {},
        )
    }
}

@Composable
@Preview
private fun BlackButtonWithBothIconsPreview() {
    MindplexTheme {
        MidplexButton(
            modifier = Modifier.fillMaxWidth(),
            buttonLabel = "BlackButton",
            startIcon = {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                )
            },
            endIcon = {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                )
            },
            onClick = {},
        )
    }
}
