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
                const val FACTS_API = "facts_api"
                const val USER_TOKEN = "user_token"

                object FactsAPI {
                    const val API_KEY = "api_key"
                }
                object UserToken {
                    const val TOKEN = "token"
                }
            }
        }
    }
}
