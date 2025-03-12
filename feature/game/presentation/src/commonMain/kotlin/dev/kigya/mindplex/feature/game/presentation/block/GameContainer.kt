package dev.kigya.mindplex.feature.game.presentation.block

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import dev.kigya.mindplex.feature.game.presentation.ui.theme.GameTheme

@Composable
internal fun GameContainer(content: @Composable ColumnScope.() -> Unit) = Column(
    modifier = Modifier
        .fillMaxSize()
        .statusBarsPadding()
        .padding(
            horizontal = GameTheme.dimension.dp24.value,
            vertical = GameTheme.dimension.dp16.value,
        )
        .testTag("game_section"),
    horizontalAlignment = Alignment.CenterHorizontally,
    content = content,
)
