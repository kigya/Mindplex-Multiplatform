@file:OptIn(ExperimentalResourceApi::class)

package dev.kigya.mindplex.core.presentation.resources

import mindplex_multiplatform.core.presentation.resources.generated.resources.Res
import mindplex_multiplatform.core.presentation.resources.generated.resources.im_onboarding_first
import mindplex_multiplatform.core.presentation.resources.generated.resources.im_onboarding_second
import mindplex_multiplatform.core.presentation.resources.generated.resources.im_onboarding_third
import mindplex_multiplatform.core.presentation.resources.generated.resources.nunito_extrabold
import mindplex_multiplatform.core.presentation.resources.generated.resources.onboarding_first_description
import mindplex_multiplatform.core.presentation.resources.generated.resources.onboarding_first_title
import mindplex_multiplatform.core.presentation.resources.generated.resources.onboarding_get_started_button_text
import mindplex_multiplatform.core.presentation.resources.generated.resources.onboarding_next_button_text
import mindplex_multiplatform.core.presentation.resources.generated.resources.onboarding_second_description
import mindplex_multiplatform.core.presentation.resources.generated.resources.onboarding_second_title
import mindplex_multiplatform.core.presentation.resources.generated.resources.onboarding_skip_button_text
import mindplex_multiplatform.core.presentation.resources.generated.resources.onboarding_third_description
import mindplex_multiplatform.core.presentation.resources.generated.resources.onboarding_third_title
import mindplex_multiplatform.core.presentation.resources.generated.resources.rubik_medium
import mindplex_multiplatform.core.presentation.resources.generated.resources.rubik_regular
import mindplex_multiplatform.core.presentation.resources.generated.resources.splash_title
import org.jetbrains.compose.resources.ExperimentalResourceApi

/**
 * `dev.kigya.mindplex.core.presentation.resources.ResourceProvider`
 * is an object designed to centralize and simplify the access to various resources
 * in project. For now Kotlin Multiplatform does not have a support of modular resources.
 *
 * The use of `@OptIn(ExperimentalResourceApi::class)` indicates that this object makes use
 * of experimental API features that may change in future releases.
 *
 * Structure:
 * - Strings: Contains references to all string resources used throughout the application.
 * - Drawable: Contains references to drawable resources such as images.
 * - Lottie: Provides functionality to load Lottie animation files and store animation file paths.
 * - Font: Contains references to font resources used within the application.
 */
@OptIn(ExperimentalResourceApi::class)
object ResourceProvider {
    object Strings {
        val SPLASH_TITLE = Res.string.splash_title
        val ONBOARDING_FIRST_TITLE = Res.string.onboarding_first_title
        val ONBOARDING_FIRST_DESCRIPTION = Res.string.onboarding_first_description
        val ONBOARDING_SECOND_TITLE = Res.string.onboarding_second_title
        val ONBOARDING_SECOND_DESCRIPTION = Res.string.onboarding_second_description
        val ONBOARDING_THIRD_TITLE = Res.string.onboarding_third_title
        val ONBOARDING_THIRD_DESCRIPTION = Res.string.onboarding_third_description
        val ONBOARDING_SKIP_BUTTON_TEXT = Res.string.onboarding_skip_button_text
        val ONBOARDING_NEXT_BUTTON_TEXT = Res.string.onboarding_next_button_text
        val ONBOARDING_GET_STARTED_BUTTON_TEXT = Res.string.onboarding_get_started_button_text
    }

    object Drawable {
        val IMAGE_ONBOARDING_FIRST = Res.drawable.im_onboarding_first
        val IMAGE_ONBOARDING_SECOND = Res.drawable.im_onboarding_second
        val IMAGE_ONBOARDING_THIRD = Res.drawable.im_onboarding_third
    }

    object Lottie {
        const val MINDPLEX_LOGO = "files/mindplex_logo.json"
        const val ONBOARDING_FIRST = "files/onboarding_first.json"
        const val ONBOARDING_SECOND = "files/onboarding_second.json"
        const val ONBOARDING_THIRD = "files/onboarding_third.json"

        suspend fun loadAsByteArray(path: String): ByteArray = Res.readBytes(path)
    }

    object Font {
        val NUNITO_EXTRABOLD = Res.font.nunito_extrabold
        val RUBIK_MEDIUM = Res.font.rubik_medium
        val RUBIK_REGULAR = Res.font.rubik_regular
    }
}
