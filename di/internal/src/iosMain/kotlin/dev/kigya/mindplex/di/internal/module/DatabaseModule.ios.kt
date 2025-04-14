package dev.kigya.mindplex.di.internal.module

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import dev.kigya.mindplex.core.data.profile.database.UserProfileDatabase
import dev.kigya.mindplex.feature.game.data.database.QuestionsDatabase
import dev.kigya.mindplex.feature.home.data.database.FactsDatabase
import dev.kigya.mindplex.feature.profile.data.database.ProfileDatabase
import dev.kigya.mindplex.feature.profile.data.database.UserRankDatabase
import org.koin.dsl.module
import platform.Foundation.NSHomeDirectory

actual val databaseModule = module {
    single {
        Room.databaseBuilder<UserProfileDatabase>(
            name = NSHomeDirectory() + "/${UserProfileDatabase.DATABASE_NAME}.db",
        ).setDriver(BundledSQLiteDriver()).build()
    }

    single { get<UserProfileDatabase>().dao }

    single {
        Room.databaseBuilder<FactsDatabase>(
            name = NSHomeDirectory() + "/${FactsDatabase.DATABASE_NAME}.db",
        ).setDriver(BundledSQLiteDriver()).build()
    }

    single { get<FactsDatabase>().dao }

    single {
        Room.databaseBuilder<QuestionsDatabase>(
            name = NSHomeDirectory() + "/${QuestionsDatabase.DATABASE_NAME}.db",
        ).setDriver(BundledSQLiteDriver()).build()
    }

    single { get<QuestionsDatabase>().dao }

    single {
        Room.databaseBuilder<UserRankDatabase>(
            name = NSHomeDirectory() + "/${UserRankDatabase.DATABASE_NAME}.db",
        ).setDriver(BundledSQLiteDriver()).build()
    }

    single { get<UserRankDatabase>().dao }

    single {
        Room.databaseBuilder<ProfileDatabase>(
            name = NSHomeDirectory() + "/${ProfileDatabase.DATABASE_NAME}.db",
        ).setDriver(BundledSQLiteDriver()).build()
    }

    single { get<ProfileDatabase>().dao }
}
