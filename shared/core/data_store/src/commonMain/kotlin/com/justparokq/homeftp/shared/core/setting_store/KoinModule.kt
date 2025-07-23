package com.justparokq.homeftp.shared.core.setting_store

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope
import org.koin.dsl.module

val settingStoreModule = module {
    single<DataStore<Preferences>> {
        createDataStore(context = getContextObject())
    }
    single<NetworkStore> {
        NetworkStoreImpl(dataStore = get())
    }
}

fun Scope.getContextObject(): Any {
    return get(named("Context"))
}