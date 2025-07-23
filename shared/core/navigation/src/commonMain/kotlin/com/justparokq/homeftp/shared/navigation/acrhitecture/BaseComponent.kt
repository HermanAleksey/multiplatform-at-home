package com.justparokq.homeftp.shared.navigation.acrhitecture

import com.arkivanov.decompose.value.Value

interface BaseComponent<State : BaseComponentState, Intent : BaseComponentIntent> :
    IInputActionDelegate {

    val state: Value<State>

    fun processIntent(intent: Intent)
}

interface BaseComponentState

interface BaseComponentIntent


// by default screen does not process any inputs
// returns [false] if nothing was done and [true] if action was processed
interface IInputActionDelegate {

    fun processNextInput(): Boolean {
        return false
    }

    fun processBackInput(): Boolean {
        return false
    }
}