package com.justparokq.homeftp.shared.navigation.acrhitecture

import com.arkivanov.decompose.value.Value

interface BaseComponent<State : BaseComponentState, Intent : BaseComponentIntent> {

    val state: Value<State>

    fun processIntent(intent: Intent)
}

interface BaseComponentState

interface BaseComponentIntent