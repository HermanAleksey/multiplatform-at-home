package com.justpoarokq.shared.core.base_database.repository

import com.justparokq.homeftp.shared.core.setting_key.Setting
import com.justpoarokq.shared.core.base_database.api.NetworkSettingsInteractor
import com.justpoarokq.shared.core.base_database.api.SettingModel
import com.justpoarokq.shared.core.base_database.api.SettingsRepository

/**
 * Repository for managing the current network target (Mock, Dev, Prod).
 * Uses SettingsRepository to persist and retrieve the value.
 */
internal class NetworkSettingsInteractorImpl(
    private val settingsRepository: SettingsRepository,
) : NetworkSettingsInteractor {

    override suspend fun getTargetOption(): Setting.NetworkKey.Target.Option {
        val value = settingsRepository.getStringSettingValue(Setting.NetworkKey.Target.key)
            ?: error("Settings database does not contain ${Setting.NetworkKey.Target.key} key.")
        return Setting.NetworkKey.Target.Option.fromString(value)
            ?: error("Settings database contains ${Setting.NetworkKey.Target.key} key with invalid value: $value.")
    }

    /**
     * Sets the current network target option.
     */
    override suspend fun setTargetOption(value: Setting.NetworkKey.Target.Option) {
        settingsRepository.updateSetting(
            name = Setting.NetworkKey.Target.key,
            setting = SettingModel(
                name = Setting.NetworkKey.Target.key,
                description = "Network target environment",
                category = SettingModel.Category.Network,
                value = SettingModel.Value.String(value.toStringValue())
            )
        )
    }
} 