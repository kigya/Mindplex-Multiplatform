package dev.kigya.mindplex.feature.login.domain.contract

interface ProfileImageInterceptorContract {
    fun intercept(url: String?): String
}
