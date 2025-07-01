package com.justparokq.homeftp.shared.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

actual fun getMultiplatformMainThread(): CoroutineDispatcher {
    return Dispatchers.Main
}