package dev.kigya.mindplex.feature.game.data.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.room.TypeConverters
import dev.kigya.mindplex.feature.game.data.dao.QuestionDao
import dev.kigya.mindplex.feature.game.data.model.local.QuestionLocal

expect object QuestionLocalConstructor : RoomDatabaseConstructor<QuestionsDatabase>

@Database(
    entities = [QuestionLocal::class],
    version = 1,
    exportSchema = false,
)
@TypeConverters(QuestionsTypeConverter::class)
@ConstructedBy(QuestionLocalConstructor::class)
abstract class QuestionsDatabase : RoomDatabase() {
    abstract val dao: QuestionDao

    companion object {
        const val DATABASE_NAME = "questions"
    }
}
