package dev.kigya.mindplex.feature.home.presentation.contract

import androidx.annotation.Size
import androidx.compose.runtime.Immutable
import dev.kigya.mindplex.core.presentation.feature.CopyableComponentState
import dev.kigya.mindplex.core.presentation.feature.UnidirectionalViewModelContract
import dev.kigya.mindplex.core.presentation.uikit.StubErrorType
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
import mindplex_multiplatform.feature.home.presentation.generated.resources.home_modes_pick_answer_description
import mindplex_multiplatform.feature.home.presentation.generated.resources.home_modes_pick_answer_title
import mindplex_multiplatform.feature.home.presentation.generated.resources.home_modes_random_description
import mindplex_multiplatform.feature.home.presentation.generated.resources.home_modes_random_title
import mindplex_multiplatform.feature.home.presentation.generated.resources.home_modes_true_of_false_description
import mindplex_multiplatform.feature.home.presentation.generated.resources.home_modes_true_of_false_title
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
import mindplex_multiplatform.feature.home.presentation.generated.resources.ic_pick_answer_mode
import mindplex_multiplatform.feature.home.presentation.generated.resources.ic_politics
import mindplex_multiplatform.feature.home.presentation.generated.resources.ic_random_mode
import mindplex_multiplatform.feature.home.presentation.generated.resources.ic_science_computers
import mindplex_multiplatform.feature.home.presentation.generated.resources.ic_science_gadgets
import mindplex_multiplatform.feature.home.presentation.generated.resources.ic_science_mathematics
import mindplex_multiplatform.feature.home.presentation.generated.resources.ic_science_nature
import mindplex_multiplatform.feature.home.presentation.generated.resources.ic_sports
import mindplex_multiplatform.feature.home.presentation.generated.resources.ic_true_or_false_mode
import mindplex_multiplatform.feature.home.presentation.generated.resources.ic_vehicles
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource

interface HomeContract :
    UnidirectionalViewModelContract<HomeContract.State, HomeContract.Event, HomeContract.Effect> {

    @ConsistentCopyVisibility
    @Immutable
    data class State internal constructor(
        val stubErrorType: StubErrorType? = null,
        val headerData: HeaderData = HeaderData(),
        val factsData: FactsData = FactsData(),
        val typesData: TypesData = TypesData(),
        val categorySelectionData: CategorySelectionData = CategorySelectionData(),
    ) : CopyableComponentState {
        @ConsistentCopyVisibility
        @Immutable
        data class HeaderData internal constructor(
            val userName: String = String.empty,
            val avatarUrl: String? = null,
            val isProfileNameLoading: Boolean = true,
            val isProfilePictureLoading: Boolean = true,
        )

        @ConsistentCopyVisibility
        @Immutable
        data class FactsData internal constructor(
            val areFactsLoading: Boolean = true,
            @Size(value = FACTS_AMOUNT.toLong())
            val facts: ImmutableList<String> = persistentListOf(),
            val currentIndex: Int = 0,
        )

        @ConsistentCopyVisibility
        @Immutable
        data class TypesData internal constructor(
            val areTypesLoading: Boolean = true,
            @HomeModesSize val types: ImmutableList<TypeConfig> = defaultTypes,
        ) {
            @ConsistentCopyVisibility
            data class TypeConfig internal constructor(
                val type: Type = Type.RANDOM,
                val icon: DrawableResource? = null,
                val title: StringResource? = null,
                val description: StringResource? = null,
                val shouldScaleIcon: Boolean = false,
                val shouldDisplayDelimiter: Boolean = false,
            ) {
                enum class Type { MULTIPLE, BOOLEAN, RANDOM }
            }

            @Suppress("DataClassContainsFunctions")
            fun getTitleByType(type: TypeConfig.Type): StringResource? =
                types.firstOrNull { it.type == type }?.title

            private companion object {
                val defaultTypes = persistentListOf(
                    TypeConfig(
                        type = TypeConfig.Type.MULTIPLE,
                        icon = Res.drawable.ic_pick_answer_mode,
                        title = Res.string.home_modes_pick_answer_title,
                        description = Res.string.home_modes_pick_answer_description,
                        shouldDisplayDelimiter = true,
                    ),
                    TypeConfig(
                        type = TypeConfig.Type.BOOLEAN,
                        icon = Res.drawable.ic_true_or_false_mode,
                        title = Res.string.home_modes_true_of_false_title,
                        description = Res.string.home_modes_true_of_false_description,
                        shouldDisplayDelimiter = true,
                    ),
                    TypeConfig(
                        type = TypeConfig.Type.RANDOM,
                        icon = Res.drawable.ic_random_mode,
                        title = Res.string.home_modes_random_title,
                        description = Res.string.home_modes_random_description,
                        shouldDisplayDelimiter = false,
                    ),
                )
            }
        }

        @ConsistentCopyVisibility
        @Immutable
        data class CategorySelectionData internal constructor(
            val shouldDisplayPopup: Boolean = false,
            val typeTitle: StringResource? = null,
            val type: TypesData.TypeConfig.Type? = null,
            @Size(value = CATEGORIES_AMOUNT.toLong())
            val categories: ImmutableList<CategoryData> = persistentListOf(),
            @Size(value = DIFFICULTIES_AMOUNT.toLong())
            val difficulties: ImmutableList<DifficultyChipData> = persistentListOf(),
        ) {

            @Immutable
            enum class CategoryData(
                val text: StringResource? = null,
                val icon: DrawableResource? = null,
            ) {
                GENERAL_KNOWLEDGE(
                    text = Res.string.home_categories_general_knowledge,
                    icon = Res.drawable.ic_general_knowledge,
                ),
                ENTERTAINMENT_BOOKS(
                    text = Res.string.home_categories_books,
                    icon = Res.drawable.ic_entertainment_books,
                ),
                ENTERTAINMENT_FILM(
                    text = Res.string.home_categories_film,
                    icon = Res.drawable.ic_entertainment_film,
                ),
                ENTERTAINMENT_MUSIC(
                    text = Res.string.home_categories_music,
                    icon = Res.drawable.ic_entertainment_music,
                ),
                ENTERTAINMENT_MUSICALS_THEATRES(
                    text = Res.string.home_categories_musicals_theatres,
                    icon = Res.drawable.ic_entertainment_musicals,
                ),
                ENTERTAINMENT_TELEVISION(
                    text = Res.string.home_categories_television,
                    icon = Res.drawable.ic_entertainment_television,
                ),
                ENTERTAINMENT_VIDEO_GAMES(
                    text = Res.string.home_categories_video_games,
                    icon = Res.drawable.ic_entertainment_video_games,
                ),
                ENTERTAINMENT_BOARD_GAMES(
                    text = Res.string.home_categories_board_games,
                    icon = Res.drawable.ic_entertainment_board_games,
                ),
                SCIENCE_NATURE(
                    text = Res.string.home_categories_science_nature,
                    icon = Res.drawable.ic_science_nature,
                ),
                SCIENCE_COMPUTERS(
                    text = Res.string.home_categories_computers,
                    icon = Res.drawable.ic_science_computers,
                ),
                SCIENCE_MATHEMATICS(
                    text = Res.string.home_categories_mathematics,
                    icon = Res.drawable.ic_science_mathematics,
                ),
                MYTHOLOGY(
                    text = Res.string.home_categories_mythology,
                    icon = Res.drawable.ic_mythology,
                ),
                SPORTS(
                    text = Res.string.home_categories_sports,
                    icon = Res.drawable.ic_sports,
                ),
                GEOGRAPHY(
                    text = Res.string.home_categories_geography,
                    icon = Res.drawable.ic_geography,
                ),
                HISTORY(
                    text = Res.string.home_categories_history,
                    icon = Res.drawable.ic_history,
                ),
                POLITICS(
                    text = Res.string.home_categories_politics,
                    icon = Res.drawable.ic_politics,
                ),
                ART(
                    text = Res.string.home_categories_art,
                    icon = Res.drawable.ic_art,
                ),
                CELEBRITIES(
                    text = Res.string.home_categories_celebrities,
                    icon = Res.drawable.ic_celebrities,
                ),
                ANIMALS(
                    text = Res.string.home_categories_animals,
                    icon = Res.drawable.ic_animals,
                ),
                VEHICLES(
                    text = Res.string.home_categories_vehicles,
                    icon = Res.drawable.ic_vehicles,
                ),
                ENTERTAINMENT_COMICS(
                    text = Res.string.home_categories_comics,
                    icon = Res.drawable.ic_entertainment_comics,
                ),
                SCIENCE_GADGETS(
                    text = Res.string.home_categories_gadgets,
                    icon = Res.drawable.ic_science_gadgets,
                ),
                ENTERTAINMENT_JAPANESE_ANIME_MANGA(
                    text = Res.string.home_categories_anime_manga,
                    icon = Res.drawable.ic_entertainment_japanese,
                ),
                ENTERTAINMENT_CARTOON_ANIMATIONS(
                    text = Res.string.home_categories_cartoon_animations,
                    icon = Res.drawable.ic_entertainment_cartoon,
                ),
            }

            @Immutable
            data class DifficultyChipData(
                val type: DifficultyType,
                val textResource: StringResource? = null,
                val isSelected: Boolean = false,
            ) {
                enum class DifficultyType {
                    EASY,
                    MEDIUM,
                    HARD,
                }
            }
        }
    }

    @Immutable
    sealed class Event {

        internal data object OnStopLifecycleEventReceived : Event()

        internal data object OnProfilePictureLoaded : Event()

        internal data object OnProfilePictureErrorReceived : Event()

        internal data object OnErrorStubClicked : Event()

        internal data class OnModeClicked(
            val type: State.TypesData.TypeConfig.Type,
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
    sealed class Effect

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
