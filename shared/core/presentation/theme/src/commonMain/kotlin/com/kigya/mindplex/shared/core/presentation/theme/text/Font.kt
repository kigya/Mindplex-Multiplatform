package com.kigya.mindplex.shared.core.presentation.theme.text

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import mindplex_multiplatform.shared.core.presentation.theme.generated.resources.Res
import mindplex_multiplatform.shared.core.presentation.theme.generated.resources.rubik_medium
import mindplex_multiplatform.shared.core.presentation.theme.generated.resources.rubik_regular
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.Font

@OptIn(ExperimentalResourceApi::class)
internal val Rubik
    @Composable
    get() = FontFamily(
        Font(
            resource = Res.font.rubik_medium,
            weight = FontWeight.Medium,
            style = FontStyle.Normal
        ),
        Font(
            resource = Res.font.rubik_regular,
            weight = FontWeight.Normal,
            style = FontStyle.Normal
        ),
    )
