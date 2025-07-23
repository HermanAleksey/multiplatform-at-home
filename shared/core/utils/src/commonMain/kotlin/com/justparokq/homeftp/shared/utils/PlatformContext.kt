package com.justparokq.homeftp.shared.utils

import androidx.compose.runtime.compositionLocalOf

val ContextFactoryComposition = compositionLocalOf<ContextFactory> { error("No context provided!") }

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class ContextFactory {
    fun getContext(): Any
    fun getApplication(): Any
    fun getActivity(): Any
}
