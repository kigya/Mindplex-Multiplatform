package dev.kigya.mindplex.feature.leaderboard.presentation.block

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.kigya.mindplex.core.presentation.uikit.MindplexText
import dev.kigya.mindplex.core.presentation.uikit.MindplexTextAnimation
import dev.kigya.mindplex.feature.leaderboard.presentation.ui.theme.LeaderboardTheme
import dev.kigya.mindplex.feature.leaderboard.presentation.ui.theme.LeaderboardTheme.leaderboardTitleText
import mindplex_multiplatform.feature.leaderboard.presentation.generated.resources.Res
import mindplex_multiplatform.feature.leaderboard.presentation.generated.resources.leaderboard
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun LeaderboardScreenHeader(modifier: Modifier = Modifier) {
    Row(modifier = modifier.fillMaxWidth()) {
        MindplexText(
            value = stringResource(Res.string.leaderboard),
            color = LeaderboardTheme.colorScheme.leaderboardTitleText,
            typography = LeaderboardTheme.typography.leaderboardTitleText,
            animation = MindplexTextAnimation.Typewriter,
        )
    }
}
