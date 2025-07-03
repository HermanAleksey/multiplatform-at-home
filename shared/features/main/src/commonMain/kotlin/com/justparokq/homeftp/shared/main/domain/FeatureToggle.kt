package com.justparokq.homeftp.shared.main.domain

import com.justparokq.homeftp.shared.core.setting_key.Setting

internal data class FeatureToggle(
    val key: Setting,
    val isEnabled: Boolean
)