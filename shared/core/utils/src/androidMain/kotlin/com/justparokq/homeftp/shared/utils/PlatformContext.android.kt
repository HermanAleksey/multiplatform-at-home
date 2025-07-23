@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package com.justparokq.homeftp.shared.utils
import androidx.activity.ComponentActivity

actual class ContextFactory(private val activity: ComponentActivity) {

    actual fun getContext(): Any = activity.baseContext
    actual fun getApplication(): Any = activity.application
    actual fun getActivity(): Any = activity
}