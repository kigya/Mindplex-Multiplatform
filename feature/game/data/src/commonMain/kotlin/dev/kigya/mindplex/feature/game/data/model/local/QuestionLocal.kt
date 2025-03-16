package dev.kigya.mindplex.feature.game.data.model.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "questions")
data class QuestionLocal(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("id")
    val id: Int = 0,
    @ColumnInfo("question")
    val question: String,
    @ColumnInfo("answers")
    val answers: List<String>,
    @ColumnInfo("correct_answer_index")
    val correctAnswerIndex: Int,
    @ColumnInfo("correct_answer")
    val correctAnswer: String,
    @ColumnInfo("difficulty")
    val difficulty: DifficultyLocal,
    @ColumnInfo("type")
    val type: TypeLocal,
    @ColumnInfo("category")
    val category: CategoryLocal,
)
