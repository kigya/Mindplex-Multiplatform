package dev.kigya.mindplex.core.data.scout.exception

sealed interface ScoutException {
    class FailedToFetchData(message: String) :
        Exception("Failed to fetch data with scout: $message"), ScoutException
}
