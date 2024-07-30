package dev.kigya.mindplex.feature.onboarding.presentation.model

import androidx.annotation.IntRange
import androidx.compose.runtime.Immutable
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource

@Immutable
data class OnboardingScreenUiModel(
    val lottiePath: String? = null,
    val lottieDrawableResource: DrawableResource? = null,
    val titleTextResource: StringResource? = null,
    val descriptionTextResource: StringResource? = null,
    @IntRange(
        from = FIRST_PAGE_INDEX.toLong(),
        to = LAST_PAGE_INDEX.toLong(),
    ) val page: Int = FIRST_PAGE_INDEX,
    val skipButtonTextResource: StringResource? = null,
    val nextButtonTextResource: StringResource? = null,
) {
    private companion object {
        const val FIRST_PAGE_INDEX = 0
        const val LAST_PAGE_INDEX = 2
    }
}
