package dev.kigya.mindplex.feature.onboarding.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import dev.kigya.mindplex.feature.onboarding.domain.contract.OnboardingRepositoryContract
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import okio.Path.Companion.toPath
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.properties.Delegates
import kotlin.test.assertEquals

class OnboardingRepositoryTest {

    private val testDispatcher = StandardTestDispatcher()

    private var dataStore by Delegates.notNull<DataStore<Preferences>>()
    private var onboardingRepository by Delegates.notNull<OnboardingRepositoryContract>()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        dataStore = PreferenceDataStoreFactory
            .createWithPath(
                produceFile = { "datastore/test_preferences.preferences_pb".toPath() },
            )
        onboardingRepository = OnboardingRepository(dataStore, testDispatcher)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        runBlocking {
            Dispatchers.resetMain()
        }
    }

    @Test
    fun isOnboardingCompletedReturnsFalseWhenNotSet() = runTest {
        // When
        val result = onboardingRepository.isOnboardingCompleted.first()

        // Then
        assertEquals(false, result)
    }

    @Test
    fun setOnboardingCompletedSetsIsOnboardingCompletedToTrue() = runTest {
        // When
        onboardingRepository.setOnboardingCompleted()
        val result = onboardingRepository.isOnboardingCompleted.first()

        // Then
        assertEquals(true, result)
    }

    @Test
    fun isOnboardingCompletedPersistsAcrossInstances() = runTest {
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

    @Test
    fun setOnboardingCompletedIsIdempotent() = runTest {
        // When
        onboardingRepository.setOnboardingCompleted()
        onboardingRepository.setOnboardingCompleted()
        val result = onboardingRepository.isOnboardingCompleted.first()

        // Then
        assertEquals(true, result)
    }
}
