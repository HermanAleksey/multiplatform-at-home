package com.justparokq.homeftp.shared.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

expect fun getMultiplatformMainThread(): CoroutineDispatcher

fun Dispatchers.MainMultiplatform(): CoroutineDispatcher {
    return getMultiplatformMainThread()
}