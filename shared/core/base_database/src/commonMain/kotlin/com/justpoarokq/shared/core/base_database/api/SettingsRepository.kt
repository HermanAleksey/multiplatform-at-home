package com.justpoarokq.shared.core.base_database.api

import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for managing application settings.
 * Provides thread-safe operations for storing, retrieving, and observing settings
 * with support for both boolean and string value types.
 */
interface SettingsRepository {

    /**
     * Retrieves the boolean value of a setting by its name.
     * 
     * @param name The unique identifier of the setting
     * @return The boolean value of the setting, or null if the setting doesn't exist
     * @throws IllegalArgumentException if the name is blank
     */
    suspend fun getBoolSettingValue(name: String): Boolean?
    
    /**
     * Retrieves the string value of a setting by its name.
     * 
     * @param name The unique identifier of the setting
     * @return The string value of the setting, or null if the setting doesn't exist
     * @throws IllegalArgumentException if the name is blank
     */
    suspend fun getStringSettingValue(name: String): String?

    /**
     * Updates or creates a setting in the database.
     * If a setting with the same name already exists, it will be updated.
     * 
     * @param name The unique identifier of the setting
     * @param setting The setting model containing the updated values
     * @throws IllegalArgumentException if the name is blank or if the setting name doesn't match the parameter
     */
    suspend fun updateSetting(name: String, setting: SettingModel)

    /**
     * Observes all settings in the database as a reactive stream.
     * The flow will emit the current list of settings immediately upon subscription
     * and then emit updates whenever any setting is modified.
     * 
     * @return A Flow that emits the complete list of all settings
     */
    suspend fun observeSettings(): Flow<List<SettingModel>>

    /**
     * Observes only feature settings (settings with Feature category) as a reactive stream.
     * The flow will emit the current list of feature settings immediately upon subscription
     * and then emit updates whenever any feature setting is modified.
     * 
     * @return A Flow that emits the list of feature settings only
     */
    suspend fun observeFeatures(): Flow<List<SettingModel>>

    /**
     * Deletes a specific setting from the database.
     * 
     * @param name The unique identifier of the setting to delete
     * @return true if the setting was found and deleted, false if it didn't exist
     * @throws IllegalArgumentException if the name is blank
     */
    suspend fun deleteSetting(name: String): Boolean

    /**
     * Resets all settings to their default values by clearing the database.
     * This operation will remove all custom settings and restore the initial state.
     * 
     * @throws IllegalStateException if the reset operation fails
     */
    suspend fun resetToDefaults()
}