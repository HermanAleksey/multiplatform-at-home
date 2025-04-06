package com.justparokq.homeftp.shared.utils

import androidx.compose.runtime.compositionLocalOf

val ContextFactoryComposition = compositionLocalOf<ContextFactory> { error("No context provided!") }

expect class ContextFactory {
    fun getContext(): Any
    fun getApplication(): Any
    fun getActivity(): Any
}
