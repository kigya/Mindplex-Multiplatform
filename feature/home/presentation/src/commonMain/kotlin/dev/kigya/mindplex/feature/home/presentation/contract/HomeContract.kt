package dev.kigya.mindplex.feature.home.presentation.contract

import androidx.annotation.Size
import androidx.compose.runtime.Immutable
import dev.kigya.mindplex.core.presentation.component.StubErrorType
import dev.kigya.mindplex.core.presentation.feature.CopyableComponentState
import dev.kigya.mindplex.core.presentation.feature.UnidirectionalViewModelContract
import dev.kigya.mindplex.core.util.extension.empty
import dev.kigya.mindplex.feature.home.presentation.contract.HomeContract.Companion.MAX_MODES_AMOUNT
import dev.kigya.mindplex.feature.home.presentation.contract.HomeContract.Companion.MIN_MODES_AMOUNT
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import mindplex_multiplatform.feature.home.presentation.generated.resources.Res
import mindplex_multiplatform.feature.home.presentation.generated.resources.home_categories_animals
import mindplex_multiplatform.feature.home.presentation.generated.resources.home_categories_anime_manga
import mindplex_multiplatform.feature.home.presentation.generated.resources.home_categories_art
import mindplex_multiplatform.feature.home.presentation.generated.resources.home_categories_board_games
import mindplex_multiplatform.feature.home.presentation.generated.resources.home_categories_books
import mindplex_multiplatform.feature.home.presentation.generated.resources.home_categories_cartoon_animations
import mindplex_multiplatform.feature.home.presentation.generated.resources.home_categories_celebrities
import mindplex_multiplatform.feature.home.presentation.generated.resources.home_categories_comics
import mindplex_multiplatform.feature.home.presentation.generated.resources.home_categories_computers
import mindplex_multiplatform.feature.home.presentation.generated.resources.home_categories_film
import mindplex_multiplatform.feature.home.presentation.generated.resources.home_categories_gadgets
import mindplex_multiplatform.feature.home.presentation.generated.resources.home_categories_general_knowledge
import mindplex_multiplatform.feature.home.presentation.generated.resources.home_categories_geography
import mindplex_multiplatform.feature.home.presentation.generated.resources.home_categories_history
import mindplex_multiplatform.feature.home.presentation.generated.resources.home_categories_mathematics
import mindplex_multiplatform.feature.home.presentation.generated.resources.home_categories_music
import mindplex_multiplatform.feature.home.presentation.generated.resources.home_categories_musicals_theatres
import mindplex_multiplatform.feature.home.presentation.generated.resources.home_categories_mythology
import mindplex_multiplatform.feature.home.presentation.generated.resources.home_categories_politics
import mindplex_multiplatform.feature.home.presentation.generated.resources.home_categories_science_nature
import mindplex_multiplatform.feature.home.presentation.generated.resources.home_categories_sports
import mindplex_multiplatform.feature.home.presentation.generated.resources.home_categories_television
import mindplex_multiplatform.feature.home.presentation.generated.resources.home_categories_vehicles
import mindplex_multiplatform.feature.home.presentation.generated.resources.home_categories_video_games
import mindplex_multiplatform.feature.home.presentation.generated.resources.ic_animals
import mindplex_multiplatform.feature.home.presentation.generated.resources.ic_art
import mindplex_multiplatform.feature.home.presentation.generated.resources.ic_celebrities
import mindplex_multiplatform.feature.home.presentation.generated.resources.ic_entertainment_board_games
import mindplex_multiplatform.feature.home.presentation.generated.resources.ic_entertainment_books
import mindplex_multiplatform.feature.home.presentation.generated.resources.ic_entertainment_cartoon
import mindplex_multiplatform.feature.home.presentation.generated.resources.ic_entertainment_comics
import mindplex_multiplatform.feature.home.presentation.generated.resources.ic_entertainment_film
import mindplex_multiplatform.feature.home.presentation.generated.resources.ic_entertainment_japanese
import mindplex_multiplatform.feature.home.presentation.generated.resources.ic_entertainment_music
import mindplex_multiplatform.feature.home.presentation.generated.resources.ic_entertainment_musicals
import mindplex_multiplatform.feature.home.presentation.generated.resources.ic_entertainment_television
import mindplex_multiplatform.feature.home.presentation.generated.resources.ic_entertainment_video_games
import mindplex_multiplatform.feature.home.presentation.generated.resources.ic_general_knowledge
import mindplex_multiplatform.feature.home.presentation.generated.resources.ic_geography
import mindplex_multiplatform.feature.home.presentation.generated.resources.ic_history
import mindplex_multiplatform.feature.home.presentation.generated.resources.ic_mythology
import mindplex_multiplatform.feature.home.presentation.generated.resources.ic_politics
import mindplex_multiplatform.feature.home.presentation.generated.resources.ic_science_computers
import mindplex_multiplatform.feature.home.presentation.generated.resources.ic_science_gadgets
import mindplex_multiplatform.feature.home.presentation.generated.resources.ic_science_mathematics
import mindplex_multiplatform.feature.home.presentation.generated.resources.ic_science_nature
import mindplex_multiplatform.feature.home.presentation.generated.resources.ic_sports
import mindplex_multiplatform.feature.home.presentation.generated.resources.ic_vehicles
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource

interface HomeContract :
    UnidirectionalViewModelContract<HomeContract.State, HomeContract.Event, HomeContract.Effect> {
    @Immutable
    data class State internal constructor(
        val stubErrorType: StubErrorType? = null,
        val headerData: HeaderData = HeaderData(),
        val factsPagerData: FactsPagerData = FactsPagerData(),
        val modesData: ModesData = ModesData(),
        val categorySelectionData: CategorySelectionData = CategorySelectionData(),
    ) : CopyableComponentState {
        @Immutable
        data class HeaderData internal constructor(
            val userName: String = String.empty,
            val avatarUrl: String? = null,
            val isProfileNameLoading: Boolean = true,
            val isProfilePictureLoading: Boolean = true,
        )

        @Immutable
        data class FactsPagerData internal constructor(
            val areFactsLoading: Boolean = true,
            @Size(value = FACTS_AMOUNT.toLong())
            val facts: ImmutableList<String> = persistentListOf(),
        )

        @Immutable
        data class ModesData internal constructor(
            val areModesLoading: Boolean = true,
            @HomeModesSize val modes: ImmutableList<Mode> = persistentListOf(),
        ) {
            data class Mode internal constructor(
                val type: Type = Type.RANDOM,
                val icon: DrawableResource? = null,
                val title: StringResource? = null,
                val description: StringResource? = null,
                val shouldScaleIcon: Boolean = false,
                val shouldDisplayDelimiter: Boolean = false,
            ) {
                enum class Type { PICK_ANSWER, TRUE_OR_FALSE, RANDOM }
            }

            @Suppress("DataClassContainsFunctions")
            fun getTitleByType(type: Mode.Type): StringResource? =
                modes.firstOrNull { it.type == type }?.title
        }

        @Immutable
        data class CategorySelectionData internal constructor(
            val shouldDisplayPopup: Boolean = false,
            val modeTitle: StringResource? = null,
            @Size(value = CATEGORIES_AMOUNT.toLong())
            val categories: ImmutableList<CategoryData> = persistentListOf(),
            @Size(value = DIFFICULTIES_AMOUNT.toLong())
            val difficulties: ImmutableList<DifficultyChipData> = persistentListOf(),
        ) {
            @Immutable
            enum class CategoryData(
                val text: StringResource? = null,
                val icon: DrawableResource? = null,
                val index: Int = 0,
            ) {
                GENERAL_KNOWLEDGE(
                    text = Res.string.home_categories_general_knowledge,
                    icon = Res.drawable.ic_general_knowledge,
                    index = 9,
                ),
                BOOKS(
                    text = Res.string.home_categories_books,
                    icon = Res.drawable.ic_entertainment_books,
                    index = 10,
                ),
                FILM(
                    text = Res.string.home_categories_film,
                    icon = Res.drawable.ic_entertainment_film,
                    index = 11,
                ),
                MUSIC(
                    text = Res.string.home_categories_music,
                    icon = Res.drawable.ic_entertainment_music,
                    index = 12,
                ),
                MUSICALS_THEATRES(
                    text = Res.string.home_categories_musicals_theatres,
                    icon = Res.drawable.ic_entertainment_musicals,
                    index = 13,
                ),
                TELEVISION(
                    text = Res.string.home_categories_television,
                    icon = Res.drawable.ic_entertainment_television,
                    index = 14,
                ),
                VIDEO_GAMES(
                    text = Res.string.home_categories_video_games,
                    icon = Res.drawable.ic_entertainment_video_games,
                    index = 15,
                ),
                BOARD_GAMES(
                    text = Res.string.home_categories_board_games,
                    icon = Res.drawable.ic_entertainment_board_games,
                    index = 16,
                ),
                SCIENCE_NATURE(
                    text = Res.string.home_categories_science_nature,
                    icon = Res.drawable.ic_science_nature,
                    index = 17,
                ),
                COMPUTERS(
                    text = Res.string.home_categories_computers,
                    icon = Res.drawable.ic_science_computers,
                    index = 18,
                ),
                MATHEMATICS(
                    text = Res.string.home_categories_mathematics,
                    icon = Res.drawable.ic_science_mathematics,
                    index = 19,
                ),
                MYTHOLOGY(
                    text = Res.string.home_categories_mythology,
                    icon = Res.drawable.ic_mythology,
                    index = 20,
                ),
                SPORTS(
                    text = Res.string.home_categories_sports,
                    icon = Res.drawable.ic_sports,
                    index = 21,
                ),
                GEOGRAPHY(
                    text = Res.string.home_categories_geography,
                    icon = Res.drawable.ic_geography,
                    index = 22,
                ),
                HISTORY(
                    text = Res.string.home_categories_history,
                    icon = Res.drawable.ic_history,
                    index = 23,
                ),
                POLITICS(
                    text = Res.string.home_categories_politics,
                    icon = Res.drawable.ic_politics,
                    index = 24,
                ),
                ART(
                    text = Res.string.home_categories_art,
                    icon = Res.drawable.ic_art,
                    index = 25,
                ),
                CELEBRITIES(
                    text = Res.string.home_categories_celebrities,
                    icon = Res.drawable.ic_celebrities,
                    index = 26,
                ),
                ANIMALS(
                    text = Res.string.home_categories_animals,
                    icon = Res.drawable.ic_animals,
                    index = 27,
                ),
                VEHICLES(
                    text = Res.string.home_categories_vehicles,
                    icon = Res.drawable.ic_vehicles,
                    index = 28,
                ),
                COMICS(
                    text = Res.string.home_categories_comics,
                    icon = Res.drawable.ic_entertainment_comics,
                    index = 29,
                ),
                GADGETS(
                    text = Res.string.home_categories_gadgets,
                    icon = Res.drawable.ic_science_gadgets,
                    index = 30,
                ),
                ANIME_MANGA(
                    text = Res.string.home_categories_anime_manga,
                    icon = Res.drawable.ic_entertainment_japanese,
                    index = 31,
                ),
                CARTOON_ANIMATIONS(
                    text = Res.string.home_categories_cartoon_animations,
                    icon = Res.drawable.ic_entertainment_cartoon,
                    index = 32,
                ),
            }

            @Immutable
            data class DifficultyChipData(
                val textResource: StringResource? = null,
                val isSelected: Boolean = false,
            )
        }
    }

    @Immutable
    sealed class Event {
        internal data object OnFirstLaunch : Event()

        internal data object OnProfilePictureLoaded : Event()

        internal data object OnProfilePictureErrorReceived : Event()

        internal data object OnErrorStubClicked : Event()

        internal data class OnModeClicked(
            val type: State.ModesData.Mode.Type,
        ) : Event()

        internal data class OnModeClickStateChanged(
            @HomeModesSize val index: Int,
            val shouldScaleIcon: Boolean,
        ) : Event()

        internal data object OnPopupDismissed : Event()

        internal data class OnCategoryClicked(
            val category: State.CategorySelectionData.CategoryData,
        ) : Event()

        internal data class OnDifficultyClicked(
            val selectedChip: State.CategorySelectionData.DifficultyChipData,
        ) : Event()
    }

    @Immutable
    sealed class Effect {
        internal data object ScrollFactsToNextPage : Effect()
    }

    companion object {
        internal const val FACTS_AMOUNT = 3
        internal const val MIN_MODES_AMOUNT = 1L
        internal const val MAX_MODES_AMOUNT = 3L
        private const val CATEGORIES_AMOUNT = 24
        private const val DIFFICULTIES_AMOUNT = 3
    }
}

@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.FIELD)
@Size(
    min = MIN_MODES_AMOUNT,
    max = MAX_MODES_AMOUNT,
)
private annotation class HomeModesSize
