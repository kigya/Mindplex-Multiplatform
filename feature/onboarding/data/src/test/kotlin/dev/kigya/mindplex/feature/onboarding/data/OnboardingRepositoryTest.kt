package dev.kigya.mindplex.feature.onboarding.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import dev.kigya.mindplex.feature.onboarding.domain.contract.OnboardingRepositoryContract
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import okio.Path.Companion.toOkioPath
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.nio.file.Files
import kotlin.properties.Delegates
import kotlin.test.assertEquals

class OnboardingRepositoryTest {

    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    private var dataStore by Delegates.notNull<DataStore<Preferences>>()
    private var onboardingRepository by Delegates.notNull<OnboardingRepositoryContract>()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        val tempDir = Files.createTempDirectory("datastore_test")
        dataStore = PreferenceDataStoreFactory.createWithPath(
            produceFile = { tempDir.resolve("test_preferences.preferences_pb").toFile().toOkioPath() },
        )
        onboardingRepository = OnboardingRepository(dataStore, testDispatcher)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() = runTest {
        testScope.launch {
            dataStore.edit(MutablePreferences::clear)
            Dispatchers.resetMain()
            cancel()
        }
    }

    @Test
    fun isOnboardingCompletedReturnsFalseWhenNotSet() = runTest {
        testScope.launch {
            // When
            val result = onboardingRepository.isOnboardingCompleted.first()

            // Then
            assertEquals(false, result)
        }
    }

    @Test
    fun setOnboardingCompletedSetsIsOnboardingCompletedToTrue() = runTest {
        testScope.launch {
            // When
            onboardingRepository.setOnboardingCompleted()
            val result = onboardingRepository.isOnboardingCompleted.first()

            // Then
            assertEquals(true, result)
        }
    }

    @Test
    fun isOnboardingCompletedPersistsAcrossInstances() = runTest {
        testScope.launch {
            // Given
            onboardingRepository.setOnboardingCompleted()
            val initialResult = onboardingRepository.isOnboardingCompleted.first()
            assertEquals(true, initialResult)

            // When
            onboardingRepository = OnboardingRepository(dataStore, testDispatcher)
            val newResult = onboardingRepository.isOnboardingCompleted.first()

            // Then
            assertEquals(true, newResult)
        }
    }

    @Test
    fun setOnboardingCompletedIsIdempotent() = runTest {
        testScope.launch {
            // When
            onboardingRepository.setOnboardingCompleted()
            onboardingRepository.setOnboardingCompleted()
            val result = onboardingRepository.isOnboardingCompleted.first()

            // Then
            assertEquals(true, result)
        }
    }
}
