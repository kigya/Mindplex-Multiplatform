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
                const val GLOBAL_RANK = "globalRank"
                const val LOCAL_RANK = "localRank"
            }
        }

        object Secrets {
            const val NAME = "secrets"

            object Document {
                const val FACTS_API = "facts_api"
                const val FLAGS_API = "flags_api"

                object FactsAPI {
                    const val API_KEY = "api_key"
                }
                object FlagsAPI {
                    const val FLAGS_KEY = "flags_key"
                }
            }
        }
    }
}
