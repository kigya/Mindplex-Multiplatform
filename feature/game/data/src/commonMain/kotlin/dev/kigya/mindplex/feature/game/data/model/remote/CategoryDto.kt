@file:OptIn(ExperimentalSerializationApi::class)

package dev.kigya.mindplex.feature.game.data.model.remote

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Serializable
internal enum class CategoryDto {
    @JsonNames("General Knowledge")
    GENERAL_KNOWLEDGE,

    @JsonNames("Entertainment: Books")
    ENTERTAINMENT_BOOKS,

    @JsonNames("Entertainment: Film")
    ENTERTAINMENT_FILM,

    @JsonNames("Entertainment: Music")
    ENTERTAINMENT_MUSIC,

    @JsonNames("Entertainment: Musicals & Theatres", "Entertainment: Musicals &amp; Theatres")
    ENTERTAINMENT_MUSICALS_THEATRES,

    @JsonNames("Entertainment: Television")
    ENTERTAINMENT_TELEVISION,

    @JsonNames("Entertainment: Video Games")
    ENTERTAINMENT_VIDEO_GAMES,

    @JsonNames("Entertainment: Board Games")
    ENTERTAINMENT_BOARD_GAMES,

    @JsonNames("Science & Nature", "Science &amp; Nature")
    SCIENCE_NATURE,

    @JsonNames("Science: Computers")
    SCIENCE_COMPUTERS,

    @JsonNames("Science: Mathematics")
    SCIENCE_MATHEMATICS,

    @JsonNames("Mythology")
    MYTHOLOGY,

    @JsonNames("Sports")
    SPORTS,

    @JsonNames("Geography")
    GEOGRAPHY,

    @JsonNames("History")
    HISTORY,

    @JsonNames("Politics")
    POLITICS,

    @JsonNames("Art")
    ART,

    @JsonNames("Celebrities")
    CELEBRITIES,

    @JsonNames("Animals")
    ANIMALS,

    @JsonNames("Vehicles")
    VEHICLES,

    @JsonNames("Entertainment: Comics")
    ENTERTAINMENT_COMICS,

    @JsonNames("Science: Gadgets")
    SCIENCE_GADGETS,

    @JsonNames("Entertainment: Japanese Anime & Manga", "Entertainment: Japanese Anime &amp; Manga")
    ENTERTAINMENT_JAPANESE_ANIME_MANGA,

    @JsonNames("Entertainment: Cartoon & Animations", "Entertainment: Cartoon &amp; Animations")
    ENTERTAINMENT_CARTOON_ANIMATIONS,
}
