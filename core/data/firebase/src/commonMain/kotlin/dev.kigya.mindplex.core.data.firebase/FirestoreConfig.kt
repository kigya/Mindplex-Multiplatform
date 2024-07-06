package dev.kigya.mindplex.core.data.firebase

data object FirestoreConfig {
    object Collection {
        object Users {
            const val NAME = "users"

            object Document {
                const val AVATAR_URL = "avatarUrl"
                const val NAME = "name"
            }
        }
    }
}
