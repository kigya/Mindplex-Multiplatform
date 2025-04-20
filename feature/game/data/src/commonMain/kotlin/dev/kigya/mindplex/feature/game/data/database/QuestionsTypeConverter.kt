package dev.kigya.mindplex.feature.game.data.database

import androidx.room.TypeConverter
import dev.kigya.mindplex.feature.game.data.model.local.CategoryLocal
import dev.kigya.mindplex.feature.game.data.model.local.DifficultyLocal
import dev.kigya.mindplex.feature.game.data.model.local.TypeLocal
import dev.kigya.outcome.getOrDefault
import dev.kigya.outcome.outcomeCatching

private const val CONVERTER_SEPARATOR = "||"

internal class QuestionsTypeConverter {

    @TypeConverter
    fun fromStringList(value: List<String>?): String =
        value?.joinToString(separator = CONVERTER_SEPARATOR).orEmpty()

    @TypeConverter
    fun toStringList(value: String): List<String> {
        if (value.isBlank()) return emptyList()
        return value.split(CONVERTER_SEPARATOR)
    }

    @TypeConverter
    fun fromDifficultyLocal(difficulty: DifficultyLocal): String = difficulty.name

    @TypeConverter
    fun toDifficultyLocal(value: String): DifficultyLocal =
        outcomeCatching { DifficultyLocal.valueOf(value) }
            .getOrDefault(DifficultyLocal.EASY)

    @TypeConverter
    fun fromTypeLocal(type: TypeLocal): String = type.name

    @TypeConverter
    fun toTypeLocal(value: String): TypeLocal = outcomeCatching { TypeLocal.valueOf(value) }
        .getOrDefault(TypeLocal.MULTIPLE)

    @TypeConverter
    fun fromCategoryLocal(category: CategoryLocal): String = category.name

    @TypeConverter
    fun toCategoryLocal(value: String): CategoryLocal =
        outcomeCatching { CategoryLocal.valueOf(value) }
            .getOrDefault(CategoryLocal.ENTERTAINMENT_MUSICALS_THEATRES)
}
