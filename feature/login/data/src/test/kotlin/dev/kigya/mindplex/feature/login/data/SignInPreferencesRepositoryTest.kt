package dev.kigya.mindplex.feature.login.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import dev.kigya.mindplex.feature.login.data.repository.repository.SignInPreferencesRepository
import dev.kigya.mindplex.feature.login.domain.contract.SignInPreferencesRepositoryContract
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
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
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class SignInPreferencesRepositoryTest {

    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)
    private var dataStore by Delegates.notNull<DataStore<Preferences>>()
    private var signInPreferencesRepository by Delegates.notNull<SignInPreferencesRepositoryContract>()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        val tempDir = Files.createTempDirectory("datastore_test")
        dataStore = PreferenceDataStoreFactory.createWithPath(
            produceFile = { tempDir.resolve("test_preferences.preferences_pb").toFile().toOkioPath() },
        )
        signInPreferencesRepository = SignInPreferencesRepository(dataStore, testDispatcher)
    }

    @After
    fun tearDown() = runTest {
        testScope.launch {
            dataStore.edit { it.clear() } // Clear the preferences
            Dispatchers.resetMain()
            cancel()
        }
    }

    @Test
    fun `isSignedIn returns true when signed in`() = runTest {
        testScope.launch {
            // Given
            val key = stringPreferencesKey(GOOGLE_ID_TOKEN)
            val googleIdToken = "test_token"
            dataStore.edit { preferences ->
                preferences[key] = googleIdToken
            }

            // When
            val result = signInPreferencesRepository.isSignedIn.first()

            // Then
            assertTrue(result)
        }
    }

    @Test
    fun `signIn sets googleIdToken in preferences`() = runTest {
        testScope.launch {
            // Given
            val googleIdToken = "test_token"

            // When
            signInPreferencesRepository.signIn(googleIdToken)
            val result = dataStore.data.map { preferences ->
                preferences[stringPreferencesKey(GOOGLE_ID_TOKEN)]
            }.first()

            // Then
            assertEquals(googleIdToken, result)
        }
    }

    @Test
    fun `signOut clears googleIdToken in preferences`() = runTest {
        testScope.launch {
            // Given
            val key = stringPreferencesKey(GOOGLE_ID_TOKEN)
            val googleIdToken = "test_token"
            dataStore.edit { preferences ->
                preferences[key] = googleIdToken
            }

            // When
            signInPreferencesRepository.signOut()
            val result = dataStore.data.map { preferences ->
                preferences[key]
            }.first()

            // Then
            assertTrue(result.isNullOrEmpty())
        }
    }

    companion object {
        private const val GOOGLE_ID_TOKEN = "google_id_token"
    }
}
