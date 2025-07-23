package com.justparokq.homeftp.shared.core.setting_store

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first
import okio.Path.Companion.toPath
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

val dataStoreModule = module {
    single<DataStore<Preferences>> {
        createDataStore(get(named("Context")))
    }
    factory {
        NetworkStoreImpl(dataStore = get())
    } bind NetworkStore::class
}

interface NetworkStore {

    suspend fun setAccessToken(token: String)

    suspend fun getAccessToken(): String?

    suspend fun setRefreshToken(token: String)

    suspend fun getRefreshToken(): String?
}

internal class NetworkStoreImpl(
    private val dataStore: DataStore<Preferences>
) : NetworkStore {

    private val ACCESS_TOKEN_KEY = stringPreferencesKey("access_token_key")
    private val REFRESH_TOKEN_KEY = stringPreferencesKey("refresh_token_key")

    override suspend fun setAccessToken(token: String) {
        dataStore.updateData {
            it.toMutablePreferences().apply {
                set(key = ACCESS_TOKEN_KEY, value = token)
            }
        }
    }

    override suspend fun getAccessToken(): String? {
        return dataStore.data.first()[ACCESS_TOKEN_KEY]
    }

    override suspend fun setRefreshToken(token: String) {
        dataStore.updateData {
            it.toMutablePreferences().apply {
                set(key = REFRESH_TOKEN_KEY, value = token)
            }
        }
    }

    override suspend fun getRefreshToken(): String? {
        return dataStore.data.first()[REFRESH_TOKEN_KEY]
    }
}


/**
 *   todo add secure storage
 *   should be singleton
 */
internal fun createDataStoreWithPath(producePath: () -> String): DataStore<Preferences> =
    PreferenceDataStoreFactory.createWithPath(
        produceFile = { producePath().toPath() }
    )

internal const val dataStoreFileName = "dice.preferences_pb"

expect fun createDataStore(context: Any?): DataStore<Preferences>
