package dev.kigya.mindplex.feature.onboarding.presentation.ui.preview

import androidx.compose.runtime.Composable
import dev.kigya.mindplex.core.presentation.common.util.StableFlow
import dev.kigya.mindplex.core.presentation.common.util.empty
import dev.kigya.mindplex.core.presentation.theme.MindplexTheme
import dev.kigya.mindplex.core.presentation.uikit.preview.factory.PreviewScreensFactory
import dev.kigya.mindplex.core.util.extension.Lambda
import dev.kigya.mindplex.feature.onboarding.presentation.contract.OnboardingContract
import dev.kigya.mindplex.feature.onboarding.presentation.ui.OnboardingScreenContent
import kotlinx.collections.immutable.persistentListOf
import mindplex_multiplatform.feature.onboarding.presentation.generated.resources.Res
import mindplex_multiplatform.feature.onboarding.presentation.generated.resources.onboarding_first_description
import mindplex_multiplatform.feature.onboarding.presentation.generated.resources.onboarding_first_title
import mindplex_multiplatform.feature.onboarding.presentation.generated.resources.onboarding_get_started_button_text
import mindplex_multiplatform.feature.onboarding.presentation.generated.resources.onboarding_next_button_text
import mindplex_multiplatform.feature.onboarding.presentation.generated.resources.onboarding_second_description
import mindplex_multiplatform.feature.onboarding.presentation.generated.resources.onboarding_second_title
import mindplex_multiplatform.feature.onboarding.presentation.generated.resources.onboarding_skip_button_text
import mindplex_multiplatform.feature.onboarding.presentation.generated.resources.onboarding_third_description
import mindplex_multiplatform.feature.onboarding.presentation.generated.resources.onboarding_third_title

@PreviewScreensFactory
@Composable
private fun OnboardingScreenPreview() {
    MindplexTheme {
        OnboardingScreenContent(
            state = OnboardingContract.State(
                onboardingData = persistentListOf(
                    OnboardingContract.State.OnboardingScreenData(
                        lottiePath = "files/onboarding_first.json",
                        titleTextResource = Res.string.onboarding_first_title,
                        descriptionTextResource = Res.string.onboarding_first_description,
                        page = 0,
                        skipButtonTextResource = Res.string.onboarding_skip_button_text,
                        nextButtonTextResource = Res.string.onboarding_next_button_text,
                    ),
                    OnboardingContract.State.OnboardingScreenData(
                        lottiePath = "files/onboarding_second.json",
                        titleTextResource = Res.string.onboarding_second_title,
                        descriptionTextResource = Res.string.onboarding_second_description,
                        page = 1,
                        skipButtonTextResource = Res.string.onboarding_skip_button_text,
                        nextButtonTextResource = Res.string.onboarding_next_button_text,
                    ),
                    OnboardingContract.State.OnboardingScreenData(
                        lottiePath = "files/onboarding_third.json",
                        titleTextResource = Res.string.onboarding_third_title,
                        descriptionTextResource = Res.string.onboarding_third_description,
                        page = 2,
                        skipButtonTextResource = null,
                        nextButtonTextResource = Res.string.onboarding_get_started_button_text,
                    ),
                ),
                shouldDisplayTitle = true,
                shouldDisplayDescription = true,
                shouldDisplayDotsIndicator = true,
            ),
            event = Lambda.noOpConsumer(),
            effect = StableFlow.empty(),
        )
    }
}
