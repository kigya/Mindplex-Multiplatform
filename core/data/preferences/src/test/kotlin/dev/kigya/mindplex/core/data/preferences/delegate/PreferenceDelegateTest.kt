package dev.kigya.mindplex.core.data.preferences.delegate

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.preferencesOf
import androidx.datastore.preferences.core.stringPreferencesKey
import dev.kigya.mindplex.core.data.preferences.converter.NoOpValueConverter
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import kotlin.reflect.KProperty
import kotlin.test.DefaultAsserter.assertEquals
import kotlin.test.Test

class PreferenceDelegateTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher = UnconfinedTestDispatcher()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cancel()
    }

    @Test
    fun `retrieveDataStoreProperty should correctly handle data retrieval`() = runTest {
        // Given
        val mockDataStore = mockk<DataStore<Preferences>>(relaxed = true)
        val key = "uiTheme"
        val prefKey = stringPreferencesKey(key)
        val converter = NoOpValueConverter<String>()

        coEvery { mockDataStore.data } returns flowOf(preferencesOf(prefKey to "Light"))

        // When
        val propertyDelegate = mockDataStore.retrieveDataStoreProperty(
            valueSetter = { store, _ -> store },
            valueGetter = { "Light" },
            converter = converter,
        )
        val property = mockk<KProperty<*>>()
        val flow = propertyDelegate.getValue(mockDataStore, property).take(1)

        // Then
        flow.collect { value ->
            assertEquals(
                expected = "Light",
                actual = value,
                message = "The value collected from the flow should be 'Light'",
            )
        }
    }

    @Test
    fun `setValue should correctly update data store values`() = runTest {
        // Given
        val prefKey = stringPreferencesKey("uiTheme")
        val initialState = preferencesOf(prefKey to "Default")
        val stateFlow = MutableStateFlow(initialState)

        val mockDataStore = mockk<DataStore<Preferences>>(relaxed = true) {
            coEvery { data } returns stateFlow
        }

        val converter = NoOpValueConverter<String>()
        val propertyDelegate = mockDataStore.retrieveDataStoreProperty(
            valueSetter = { _, newValue ->
                preferencesOf(prefKey to newValue)
            },
            valueGetter = { it[prefKey] ?: "Default" },
            converter = converter,
        )

        val property = mockk<KProperty<*>>()

        // When
        propertyDelegate.setValue(mockDataStore, property, flowOf("Dark"))
        stateFlow.value = preferencesOf(prefKey to "Dark")

        // Then
        val savedValue = stateFlow.value[prefKey]
        assertEquals(
            expected = "Dark",
            actual = savedValue,
            message = "DataStore should have updated value 'Dark'",
        )
    }
}
