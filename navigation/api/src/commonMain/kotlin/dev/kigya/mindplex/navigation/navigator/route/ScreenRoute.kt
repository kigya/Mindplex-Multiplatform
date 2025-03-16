package dev.kigya.mindplex.navigation.navigator.route

import kotlinx.serialization.Serializable

sealed interface ScreenRoute {
    @Serializable
    data object Splash : ScreenRoute

    @Serializable
    data object Onboarding : ScreenRoute

    @Serializable
    data object Login : ScreenRoute

    @Serializable
    data object Home : ScreenRoute

    @Serializable
    data object Leaderboard : ScreenRoute

    @Serializable
    data object Profile : ScreenRoute

    @Serializable
    data class Game(
        val category: CategoryPresentationModel? = null,
        val difficulty: DifficultyPresentationModel? = null,
        val type: TypePresentationModel,
    ) : ScreenRoute {

        @Serializable
        enum class CategoryPresentationModel {
            GENERAL_KNOWLEDGE,
            ENTERTAINMENT_BOOKS,
            ENTERTAINMENT_FILM,
            ENTERTAINMENT_MUSIC,
            ENTERTAINMENT_MUSICALS_THEATRES,
            ENTERTAINMENT_TELEVISION,
            ENTERTAINMENT_VIDEO_GAMES,
            ENTERTAINMENT_BOARD_GAMES,
            SCIENCE_NATURE,
            SCIENCE_COMPUTERS,
            SCIENCE_MATHEMATICS,
            MYTHOLOGY,
            SPORTS,
            GEOGRAPHY,
            HISTORY,
            POLITICS,
            ART,
            CELEBRITIES,
            ANIMALS,
            VEHICLES,
            ENTERTAINMENT_COMICS,
            SCIENCE_GADGETS,
            ENTERTAINMENT_JAPANESE_ANIME_MANGA,
            ENTERTAINMENT_CARTOON_ANIMATIONS,
        }

        @Serializable
        enum class DifficultyPresentationModel {
            EASY,
            MEDIUM,
            HARD,
        }

        @Serializable
        enum class TypePresentationModel {
            BOOLEAN,
            MULTIPLE,
            RANDOM,
        }
    }
}
