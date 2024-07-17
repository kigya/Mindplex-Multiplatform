package dev.kigya.mindplex.feature.login.data

import com.wonddak.jwt.JwtParser
import dev.kigya.mindplex.feature.login.data.repository.handler.JwtHandler
import io.mockk.every
import io.mockk.mockkObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.properties.Delegates
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class JwtHandlerTest {

    private val testDispatcher = StandardTestDispatcher()
    private var jwtHandler by Delegates.notNull<JwtHandler>()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        jwtHandler = JwtHandler(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `decodeSubject returns subject when JWT token is valid`() = runTest {
        // Given
        val jwtToken = "valid.jwt.token"
        val subject = "test_subject"
        val jsonObject = JsonObject(mapOf("sub" to JsonPrimitive(subject)))

        mockkObject(JwtParser)
        every { JwtParser.parseToJsonObject(jwtToken) } returns jsonObject

        // When
        val result = jwtHandler.decodeSubject(jwtToken).getOrNull()

        // Then
        assertEquals(subject, result)
    }

    @Test
    fun `decodeSubject returns error when JWT token is invalid`() = runTest {
        // Given
        val jwtToken = "invalid.jwt.token"

        mockkObject(JwtParser)
        every { JwtParser.parseToJsonObject(jwtToken) } returns null

        // When
        val result = jwtHandler.decodeSubject(jwtToken)

        // Then
        assertTrue(result.isFailure)
        assertEquals("Invalid JWT token", result.exceptionOrNull()?.message)
    }

    @Test
    fun `decodeSubject returns error when subject is missing`() = runTest {
        // Given
        val jwtToken = "jwt.token.without.subject"
        val jsonObject = JsonObject(mapOf("not_sub" to JsonPrimitive("value")))

        mockkObject(JwtParser)
        every { JwtParser.parseToJsonObject(jwtToken) } returns jsonObject

        // When
        val result = jwtHandler.decodeSubject(jwtToken)

        // Then
        assertTrue(result.isFailure)
        assertEquals("Subject not found in JWT token", result.exceptionOrNull()?.message)
    }

    @Test
    fun `decodeSubject returns error when JWT token is empty`() = runTest {
        // Given
        val jwtToken = ""

        // When
        val result = jwtHandler.decodeSubject(jwtToken)

        // Then
        assertTrue(result.isFailure)
        assertEquals("Invalid JWT token", result.exceptionOrNull()?.message)
    }

    @Test
    fun `decodeSubject returns error when JWT token is malformed`() = runTest {
        // Given
        val jwtToken = "malformed.jwt.token"

        mockkObject(JwtParser)
        every { JwtParser.parseToJsonObject(jwtToken) } throws IllegalArgumentException("Malformed token")

        // When
        val result = jwtHandler.decodeSubject(jwtToken)

        // Then
        assertTrue(result.isFailure)
        assertEquals("Malformed token", result.exceptionOrNull()?.message)
    }
}
