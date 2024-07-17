package dev.kigya.mindplex.core.presentation.component.preview

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewFontScale
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewScreenSizes

@Preview
@PreviewScreenSizes
@PreviewFontScale
@PreviewLightDark
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.SOURCE)
annotation class ScreenPreviewFactory

@Preview
@PreviewFontScale
@PreviewLightDark
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.SOURCE)
annotation class ComponentPreviewFactory
