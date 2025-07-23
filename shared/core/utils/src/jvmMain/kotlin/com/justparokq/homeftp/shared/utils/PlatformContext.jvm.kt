package com.justparokq.homeftp.shared.utils

actual class ContextFactory {
    actual fun getContext(): Any {
        return Unit
    }

    actual fun getApplication(): Any {
        return Unit
    }

    actual fun getActivity(): Any {
        return Unit
    }
}