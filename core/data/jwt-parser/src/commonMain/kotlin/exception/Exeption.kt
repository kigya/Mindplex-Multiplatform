package exception

class InvalidJwtStructureException(message: String, cause: Throwable? = null) : Exception(message, cause)
class Base64DecodingException(message: String, cause: Throwable? = null) : Exception(message, cause)
class JsonParsingException(message: String, cause: Throwable? = null) : Exception(message, cause)
