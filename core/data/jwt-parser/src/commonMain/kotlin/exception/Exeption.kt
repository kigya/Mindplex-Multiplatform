package exception

internal class InvalidJwtStructureException(message: String, cause: Throwable? = null) : Exception(message, cause)
internal class Base64DecodingException(message: String, cause: Throwable? = null) : Exception(message, cause)
internal class JsonParsingException(message: String, cause: Throwable? = null) : Exception(message, cause)
