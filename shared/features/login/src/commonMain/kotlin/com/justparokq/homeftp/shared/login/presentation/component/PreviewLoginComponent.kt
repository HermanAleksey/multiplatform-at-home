package com.justparokq.homeftp.shared.login.presentation.component

import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.justparokq.homeftp.shared.login.api.Active
import com.justparokq.homeftp.shared.login.api.LoginComponent
import com.justparokq.homeftp.shared.login.api.LoginComponentIntent
import com.justparokq.homeftp.shared.login.api.LoginComponentState

internal class PreviewLoginComponent : LoginComponent {
    override val state: Value<LoginComponentState>
        get() = MutableValue(Active())

    override fun processIntent(intent: LoginComponentIntent) {
//        TODO("Not yet implemented")
    }
}