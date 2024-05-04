package dev.kigya.mindplex.core.data.preferences.extension

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import dev.kigya.mindplex.core.data.preferences.converter.ValueConverter
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlin.test.DefaultAsserter.assertEquals
import kotlin.test.Test

class PreferenceExtensionTest {
    @Test
    fun `getOrDefault should return stored value when key exists`() = runTest {
        // Given
        val preferences = mockk<Preferences>()
        val key = stringPreferencesKey("uiTheme")
        val storedValue = "Dark"

        every { preferences.contains(key) } returns true
        every { preferences[key] } returns storedValue

        // When
        val result = preferences.getOrDefault(key, "Light")

        // Then
        assertEquals(
            expected = "Dark",
            actual = result,
            message = "Should return the stored value when key exists",
        )
    }

    @Test
    fun `getOrDefault should return default value when key does not exist`() = runTest {
        // Given
        val preferences = mockk<Preferences>()
        val key = stringPreferencesKey("uiTheme")

        every { preferences.contains(key) } returns false

        // When
        val result = preferences.getOrDefault(key, "Light")

        // Then
        assertEquals(
            expected = "Light",
            actual = result,
            message = "Should return the default value when key does not exist",
        )
    }

    @Test
    fun `getOrDefault with converter should correctly convert and return value`() = runTest {
        // Given
        val preferences = mockk<Preferences>()
        val key = stringPreferencesKey("uiTheme")
        val storedValue = "Dark"
        val converter = mockk<ValueConverter<String, String>>()

        every { preferences.contains(key) } returns true
        every { preferences[key] } returns storedValue
        every { converter.toOriginal(storedValue) } returns "ConvertedDark"

        // When
        val result = preferences.getOrDefault(key, "Light", converter)

        // Then
        assertEquals(
            expected = "ConvertedDark",
            actual = result,
            message = "Should return the converted value when key exists",
        )
    }
}
