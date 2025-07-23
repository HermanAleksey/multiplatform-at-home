package com.justparokq.homeftp.shared.core.setting_store

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import java.io.File

actual fun createDataStore(context: Any?): DataStore<Preferences> = createDataStoreWithPath(
    // todo check dir: will be a problem with creating .dmg/.exe builds? Persist across launches
    producePath = {
        val file = File(System.getProperty("java.io.tmpdir"), dataStoreFileName)
        file.absolutePath
    }
)