package dev.kigya.mindpex.feature.home.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@Entity(tableName = "facts")
data class FactLocal(
    @PrimaryKey(autoGenerate = true) @ColumnInfo("id") val id: Int = 0,
    @ColumnInfo("fact") val fact: String,
    @ColumnInfo("timestamp") val timestamp: LocalDate = Clock.System.now().toLocalDateTime(TimeZone.UTC).date,
)
