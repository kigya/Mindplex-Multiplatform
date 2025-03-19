package parser.contract

interface JwtHandlerContract {
    suspend fun decodeSubject(jwtToken: String): Result<String>
}