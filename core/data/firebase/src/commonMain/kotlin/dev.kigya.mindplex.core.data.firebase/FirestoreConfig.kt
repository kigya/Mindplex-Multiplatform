package dev.kigya.mindplex.core.data.firebase

data object FirestoreConfig {
    object Collection {
        object Users {
            const val NAME = "users"

            object Document {
                const val AVATAR_URL = "avatarUrl"
                const val NAME = "name"
                const val SCORE = "score"
                const val COUNTRY_CODE = "countryCode"
            }
        }

        object Secrets {
            const val NAME = "secrets"

            object Document {
                const val FLAGS_API = "flags_api"

                object FlagsAPI {
                    const val FLAGS_KEY = "flags_key"
                }
            }
        }
    }
}
