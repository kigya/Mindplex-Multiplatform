package dev.kigya.mindplex.feature.home.data.exception

class FailedToFetchFactException(message: String?) : Exception("Failed to fetch fact: $message")
class FailedToProvideApiKeyException(message: String?) : Exception("Failed to provide facts api key: $message")
