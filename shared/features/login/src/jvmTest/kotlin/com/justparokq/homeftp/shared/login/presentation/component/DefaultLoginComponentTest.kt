@file:Suppress("UnusedFlow")

package com.justparokq.homeftp.shared.login.presentation.component

import com.arkivanov.decompose.ComponentContext
import com.justparokq.homeftp.shared.common.Result
import com.justparokq.homeftp.shared.core.setting_key.Setting
import com.justparokq.homeftp.shared.login.LoginRequest
import com.justparokq.homeftp.shared.login.LoginResponse
import com.justparokq.homeftp.shared.login.api.Active
import com.justparokq.homeftp.shared.login.api.Initialize
import com.justparokq.homeftp.shared.login.api.OnLoginButtonClick
import com.justparokq.homeftp.shared.login.api.OnPasswordFieldUpdated
import com.justparokq.homeftp.shared.login.api.OnUsernameFieldUpdated
import com.justparokq.homeftp.shared.login.network.LoginRepository
import com.justparokq.homeftp.shared.navigation.acrhitecture.InitHelper
import com.justparokq.homeftp.shared.navigation.feature.FeatureNavigator
import com.justparokq.homeftp.shared.navigation.feature.ProjectFeature
import com.justpoarokq.shared.core.base_database.api.NetworkSettingsInteractor
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerifySequence
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class DefaultLoginComponentTest {

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var component: DefaultLoginComponent
    private val loginRepository: LoginRepository = mockk()
    private val featureNavigator: FeatureNavigator = mockk()
    private val settingsInteractor: NetworkSettingsInteractor = mockk()
    private val initHelper: InitHelper = mockk(relaxed = true)
    private val fakeComponentContext = mockk<ComponentContext>(relaxUnitFun = true) {
        every { lifecycle } returns mockk(relaxed = true)
    }

    @BeforeTest
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        component = DefaultLoginComponent(
            componentContext = fakeComponentContext,
            loginRepository = loginRepository,
            featureNavigator = featureNavigator,
            settingsInteractor = settingsInteractor,
            initHelper = initHelper,

            )
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `empty username triggers error`() {
        component.processIntent(OnUsernameFieldUpdated(""))
        component.processIntent(OnPasswordFieldUpdated("password"))
        val expectedState = Active(
            usernameTextField = "",
            passwordTextField = "password",
            isLoading = false,
            errorMessage = "Username cannot be empty",
            networkOptionState = null
        )

        component.processIntent(OnLoginButtonClick)

        assertEquals(expectedState, component.state.value)
    }

    @Test
    fun `empty password triggers error`() {
        component.processIntent(OnUsernameFieldUpdated("user"))
        component.processIntent(OnPasswordFieldUpdated(""))
        val expectedState = Active(
            usernameTextField = "user",
            passwordTextField = "",
            isLoading = false,
            errorMessage = "Password cannot be empty",
            networkOptionState = null
        )

        component.processIntent(OnLoginButtonClick)

        assertEquals(expectedState, component.state.value)
    }

    @Test
    fun `login button click triggers repository call`() = runTest(testDispatcher) {
        coEvery { settingsInteractor.getTargetOption() } returns Setting.NetworkKey.Target.Option.Dev
        coEvery { loginRepository.sendLoginRequest(any()) } returns flow {}
        component.processIntent(OnUsernameFieldUpdated("user"))
        component.processIntent(OnPasswordFieldUpdated("password"))

        component.processIntent(OnLoginButtonClick)

        advanceUntilIdle()
        coVerifySequence {
            loginRepository.sendLoginRequest(LoginRequest(username = "user", password = "password"))
        }
    }

    @Test
    fun `input trimming works correctly`() = runTest(testDispatcher) {
        coEvery { loginRepository.sendLoginRequest(any()) } returns flow {}
        component.processIntent(OnUsernameFieldUpdated("  user  "))
        component.processIntent(OnPasswordFieldUpdated("  password  "))
        component.processIntent(OnLoginButtonClick)

        advanceUntilIdle()
        coVerifySequence {
            loginRepository.sendLoginRequest(LoginRequest(username = "user", password = "password"))
        }
    }

    @Test
    fun `username field update clears error message`() {
        component.processIntent(OnUsernameFieldUpdated(""))
        component.processIntent(OnPasswordFieldUpdated("password"))
        component.processIntent(OnLoginButtonClick)
        component.processIntent(OnUsernameFieldUpdated("newuser"))
        val expectedState = Active(
            usernameTextField = "newuser",
            passwordTextField = "password",
            isLoading = false,
            errorMessage = null,
            networkOptionState = null
        )
        assertEquals(expectedState, component.state.value)
    }

    @Test
    fun `password field update clears error message`() {
        component.processIntent(OnUsernameFieldUpdated("user"))
        component.processIntent(OnPasswordFieldUpdated(""))
        component.processIntent(OnLoginButtonClick)
        component.processIntent(OnPasswordFieldUpdated("newpass"))
        val expectedState = Active(
            usernameTextField = "user",
            passwordTextField = "newpass",
            isLoading = false,
            errorMessage = null,
            networkOptionState = null
        )
        assertEquals(expectedState, component.state.value)
    }

    @Test
    fun `settings are loaded during initialization`() = runTest(testDispatcher) {
        coEvery { settingsInteractor.getTargetOption() } returns Setting.NetworkKey.Target.Option.Dev
        val expectedState = Active(
            usernameTextField = "",
            passwordTextField = "",
            isLoading = false,
            errorMessage = null,
            networkOptionState = Active.NetworkOptionState(
                selectedOption = Setting.NetworkKey.Target.Option.Dev
            )
        )

        component.processIntent(Initialize)

        advanceUntilIdle()
        assertEquals(expectedState, component.state.value)
        coVerifySequence { settingsInteractor.getTargetOption() }
    }

    @Test
    fun `login error is displayed in state`() = runTest(testDispatcher) {
        coEvery { loginRepository.sendLoginRequest(any()) } returns flow {
            emit(Result.Error("Invalid credentials"))
        }
        component.processIntent(OnUsernameFieldUpdated("user"))
        component.processIntent(OnPasswordFieldUpdated("password"))
        component.processIntent(OnLoginButtonClick)

        advanceUntilIdle()

        val expectedState = Active(
            usernameTextField = "user",
            passwordTextField = "password",
            isLoading = false,
            errorMessage = "Invalid credentials",
            networkOptionState = null
        )
        assertEquals(expectedState, component.state.value)
    }

    @Test
    fun `network error is handled`() = runTest(testDispatcher) {
        coEvery { loginRepository.sendLoginRequest(any()) } returns flow {
            emit(Result.Error("Network connection failed"))
        }
        component.processIntent(OnUsernameFieldUpdated("user"))
        component.processIntent(OnPasswordFieldUpdated("password"))
        component.processIntent(OnLoginButtonClick)

        advanceUntilIdle()

        val expectedState = Active(
            usernameTextField = "user",
            passwordTextField = "password",
            isLoading = false,
            errorMessage = "Network connection failed",
            networkOptionState = null
        )
        assertEquals(expectedState, component.state.value)
    }

    @Test
    fun `login button is disabled during loading`() = runTest(testDispatcher) {
        coEvery { loginRepository.sendLoginRequest(any()) } returns flow {
            emit(Result.Loading(true))
        }
        component.processIntent(OnUsernameFieldUpdated("user"))
        component.processIntent(OnPasswordFieldUpdated("password"))
        component.processIntent(OnLoginButtonClick)
        advanceUntilIdle()
        val expectedState = Active(
            usernameTextField = "user",
            passwordTextField = "password",
            isLoading = true,
            errorMessage = null,
            networkOptionState = null
        )
        assertEquals(expectedState, component.state.value)
        coVerifySequence {
            loginRepository.sendLoginRequest(LoginRequest(username = "user", password = "password"))
        }
    }

    @Test
    fun `successful login navigates to main screen`() = runTest(testDispatcher) {
        coEvery { loginRepository.sendLoginRequest(any()) } returns flow {
            emit(Result.Success(LoginResponse("q")))
        }
        coJustRun { featureNavigator.replaceCurrentWith(any()) }
        component.processIntent(OnUsernameFieldUpdated("user"))
        component.processIntent(OnPasswordFieldUpdated("password"))
        component.processIntent(OnLoginButtonClick)

        advanceUntilIdle()

        verify { featureNavigator.replaceCurrentWith(ProjectFeature.MAIN) }
    }
} 