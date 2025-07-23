package com.justpoarokq.shared.core.base_database.api

import com.justparokq.homeftp.shared.core.setting_key.Setting

interface NetworkSettingsInteractor {

    suspend fun getTargetOption(): Setting.NetworkKey.Target.Option

    suspend fun setTargetOption(value: Setting.NetworkKey.Target.Option)
} 