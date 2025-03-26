package dev.kigya.mindplex.di.internal.module

import android.content.Context
import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import dev.kigya.mindplex.core.data.profile.database.UserProfileDatabase
import dev.kigya.mindplex.feature.game.data.database.QuestionsDatabase
import dev.kigya.mindplex.feature.home.data.database.FactsDatabase
import dev.kigya.mindplex.feature.leaderboard.data.database.UserPlaceDatabase
import org.koin.dsl.module

actual val databaseModule = module {
    single {
        with(get<Context>()) {
            Room.databaseBuilder<UserProfileDatabase>(
                context = applicationContext,
                name = getDatabasePath(UserProfileDatabase.DATABASE_NAME).absolutePath,
            ).setDriver(BundledSQLiteDriver()).build()
        }
    }

    single { get<UserProfileDatabase>().dao }

    single {
        with(get<Context>()) {
            Room.databaseBuilder<FactsDatabase>(
                context = applicationContext,
                name = getDatabasePath(FactsDatabase.DATABASE_NAME).absolutePath,
            ).setDriver(BundledSQLiteDriver()).build()
        }
    }

    single { get<FactsDatabase>().dao }

    single {
        with(get<Context>()) {
            Room.databaseBuilder<QuestionsDatabase>(
                context = applicationContext,
                name = getDatabasePath(QuestionsDatabase.DATABASE_NAME).absolutePath,
            ).setDriver(BundledSQLiteDriver()).build()
        }
    }

    single { get<QuestionsDatabase>().dao }

    single {
        with(get<Context>()) {
            Room.databaseBuilder<UserPlaceDatabase>(
                context = applicationContext,
                name = getDatabasePath(UserPlaceDatabase.DATABASE_NAME).absolutePath,
            ).setDriver(BundledSQLiteDriver()).build()
        }
    }

    single { get<UserPlaceDatabase>().dao }
}
