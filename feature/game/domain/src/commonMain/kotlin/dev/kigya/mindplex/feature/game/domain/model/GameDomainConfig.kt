package dev.kigya.mindplex.feature.game.domain.model

data class GameDomainConfig(
    val category: CategoryDomainModel,
    val difficulty: DifficultyDomainModel,
    val type: TypeDomainModel,
)

enum class DifficultyDomainModel {
    EASY,
    MEDIUM,
    HARD,
}

enum class TypeDomainModel {
    BOOLEAN,
    MULTIPLE,
    RANDOM,
}

enum class CategoryDomainModel {
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
