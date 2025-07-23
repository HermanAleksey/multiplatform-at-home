package com.justparokq.homeftp.shared.core.setting_store

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

actual fun createDataStore(context: Any?): DataStore<Preferences> {
    val context = (context as? Context) ?: error("Couldn't provide context")
    return createDataStoreWithPath(
        producePath = { context.filesDir.resolve(dataStoreFileName).absolutePath }
    )
}