package com.justparokq.homefpt.shared.core.network.url

import com.justparokq.homeftp.shared.core.setting_key.Setting
import com.justpoarokq.shared.core.base_database.api.NetworkSettingsInteractor

private val BASE_DEV_URL = "http://${localhostUrl}:8080/test"
private val BASE_PROD_URL = "http://${localhostUrl}:8080"

/**
 * Resolves the base URL for network requests depending on the environment.
 *
 * Use this class to obtain the correct base URL for API calls, supporting both development
 * and production environments.
 */
class UrlResolver(
    private val networkSettingsInteractor: NetworkSettingsInteractor,
) {

    /**
     * Returns the base URL for network requests based on the provided environment flag.
     *
     * @param isDevEnv If true, returns the development (test) base URL; otherwise, returns the production base URL.
     * @return The base URL as a String.
     */
    fun getBaseUrl(isDevEnv: Boolean): String {
        return if (isDevEnv) BASE_DEV_URL else BASE_PROD_URL
    }

    /**
     * Suspends and returns the base URL for network requests based on the current network settings.
     *
     * This method checks the current target option from the NetworkSettingsInteractor to determine
     * whether to use the development (test) or production base URL.
     *
     * @return The base URL as a String.
     */
    suspend fun getBaseUrl(): String {
        val currentTarget = networkSettingsInteractor.getTargetOption()
        return if (currentTarget == Setting.NetworkKey.Target.Option.Dev)
            BASE_DEV_URL
        else BASE_PROD_URL
    }
}