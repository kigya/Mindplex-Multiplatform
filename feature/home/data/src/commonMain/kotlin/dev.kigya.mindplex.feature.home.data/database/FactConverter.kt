package dev.kigya.mindplex.feature.home.data.database

import androidx.room.TypeConverter
import kotlinx.datetime.LocalDate

class FactConverter {
    @TypeConverter
    fun fromLocalDate(value: LocalDate?): String? = value?.toString()

    @TypeConverter
    fun toLocalDate(value: String?): LocalDate? = value?.let(LocalDate.Companion::parse)
}
