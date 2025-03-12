package dev.kigya.mindplex.feature.game.presentation.contract

import androidx.compose.runtime.Immutable
import dev.kigya.mindplex.core.presentation.feature.CopyableComponentState
import dev.kigya.mindplex.core.presentation.feature.UnidirectionalViewModelContract
import dev.kigya.mindplex.core.presentation.uikit.StubErrorType
import dev.kigya.mindplex.navigation.navigator.route.ScreenRoute.Game.CategoryPresentationModel
import dev.kigya.mindplex.navigation.navigator.route.ScreenRoute.Game.DifficultyPresentationModel
import dev.kigya.mindplex.navigation.navigator.route.ScreenRoute.Game.TypePresentationModel
import mindplex_multiplatform.feature.game.presentation.generated.resources.Res
import mindplex_multiplatform.feature.game.presentation.generated.resources.game_modes_pick_answer_title
import mindplex_multiplatform.feature.game.presentation.generated.resources.game_modes_random_title
import mindplex_multiplatform.feature.game.presentation.generated.resources.game_modes_true_of_false_title
import org.jetbrains.compose.resources.StringResource

interface GameContract :
    UnidirectionalViewModelContract<GameContract.State, GameContract.Event, GameContract.Effect> {

    @ConsistentCopyVisibility
    @Immutable
    data class State internal constructor(
        val stubErrorType: StubErrorType? = null,
        val isLoading: Boolean = true,
        val isValidatingAnswer: Boolean = false,
        val question: String? = null,
        val answers: List<Answer> = emptyList(),
        val category: CategoryPresentationModel = CategoryPresentationModel.GENERAL_KNOWLEDGE,
        val difficulty: DifficultyPresentationModel = DifficultyPresentationModel.EASY,
        val type: TypePresentationModel = TypePresentationModel.BOOLEAN,
        val typeTitle: StringResource? = null,
        val gameSettingType: TypePresentationModel = TypePresentationModel.MULTIPLE,
        val currentTime: Int = TIME_LIMIT,
        val score: Int = 0,
    ) : CopyableComponentState {

        @ConsistentCopyVisibility
        @Immutable
        data class Answer internal constructor(
            val text: String? = null,
            val displayType: DisplayType = DisplayType.NEUTRAL,
        ) {
            enum class DisplayType {
                CORRECT,
                INCORRECT,
                NEUTRAL,
            }
        }

        companion object {
            internal val typeTitleMap = mapOf(
                TypePresentationModel.MULTIPLE to Res.string.game_modes_pick_answer_title,
                TypePresentationModel.BOOLEAN to Res.string.game_modes_true_of_false_title,
                TypePresentationModel.RANDOM to Res.string.game_modes_random_title,
            )
        }
    }

    @Immutable
    sealed class Event {
        internal data class OnFirstLaunch(
            val type: TypePresentationModel,
            val category: CategoryPresentationModel? = null,
            val difficulty: DifficultyPresentationModel? = null,
        ) : Event()

        internal data object OnErrorStubClicked : Event()

        internal data object OnBackPressed : Event()

        internal data class OnQuestionAnswered(val answerIndex: Int) : Event()
    }

    @Immutable
    sealed class Effect

    companion object {
        internal const val TIME_LIMIT = 20
        internal const val QUESTION_DELAY = 2000L
    }
}
