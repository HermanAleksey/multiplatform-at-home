package com.justparokq.homeftp.shared.navigation.acrhitecture

/**
 * Class meant to proxy `init(){...}` calls in components
 * So you can mock it in tests and dont work around ini logic in every test
 * */
class InitHelper {

    operator fun <Intent : BaseComponentIntent> invoke(
        intentProcessor: (Intent) -> Unit,
        intent: Intent,
    ) {
        intentProcessor.invoke(intent)
    }
}