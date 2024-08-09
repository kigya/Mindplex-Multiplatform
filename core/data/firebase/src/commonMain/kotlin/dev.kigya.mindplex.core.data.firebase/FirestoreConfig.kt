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

        object Secrets {
            const val NAME = "secrets"

            object Document {
                const val FACTS_API = "facts_api"

                object FactsAPI {
                    const val API_KEY = "api_key"
                }
            }
        }
    }
}
