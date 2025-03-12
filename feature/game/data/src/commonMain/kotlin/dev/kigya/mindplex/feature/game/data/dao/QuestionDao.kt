package dev.kigya.mindplex.feature.game.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import dev.kigya.mindplex.feature.game.data.model.local.CategoryLocal
import dev.kigya.mindplex.feature.game.data.model.local.DifficultyLocal
import dev.kigya.mindplex.feature.game.data.model.local.QuestionLocal
import dev.kigya.mindplex.feature.game.data.model.local.TypeLocal

@Dao
interface QuestionDao {
    @Query(
        """
        SELECT * FROM questions
        WHERE type = :type
          AND difficulty = :difficulty
          AND category = :category
        ORDER BY RANDOM()
        LIMIT 1
    """,
    )
    suspend fun getQuestion(
        type: TypeLocal,
        difficulty: DifficultyLocal,
        category: CategoryLocal,
    ): QuestionLocal?

    @Query("SELECT COUNT(*) FROM questions")
    suspend fun getCount(): Int

    @Upsert
    suspend fun insertQuestions(questions: List<QuestionLocal>)

    @Query("SELECT * FROM questions WHERE question = :questionText LIMIT 1")
    suspend fun getQuestionByText(questionText: String): QuestionLocal?

    @Query("DELETE FROM questions")
    suspend fun clearAll()
}
