package com.lucas.petros.spotfylab.features.login.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.lucas.petros.commons.base.BaseState
import com.lucas.petros.commons.data.DataResource
import com.lucas.petros.commons.extension.handleOpt
import com.lucas.petros.network.NetworkConstants.ERROR_NETWORK
import com.lucas.petros.network.NetworkConstants.UNEXPECTED_ERROR
import com.lucas.petros.spotfylab.features.CoroutinesMainTestRule
import com.lucas.petros.spotfylab.features.login.domain.use_case.GetAuthorizationRequest
import com.lucas.petros.spotfylab.features.mock.DataMock.accessTokenMock
import getOrAwaitValue
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class LoginViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = CoroutinesMainTestRule()

    private var useCase = mockk<GetAuthorizationRequest>(relaxed = true)
    private lateinit var vm: LoginViewModel

    @Before
    fun prepare() {
        setupViewModel()
    }

    private fun setupViewModel() {
        vm = LoginViewModel(
            useCase,
        )
    }

    // Show Loading - Section
    @Test
    fun `GIVEN the initial state of ViewModel THEN showLoading is false`() {
        TestCase.assertFalse(vm.showLoading.value.handleOpt())
    }

    @Test
    fun `WHEN fun showLoading is called THEN showLoading receives the value passed in the function`() =
        runTest {

            vm.showLoading(true)
            val value = vm.showLoading.getOrAwaitValue()
            Assert.assertEquals(true, value)
        }

    // OnClick Button - Section

    @Test
    fun `GIVEN the initial state of ViewModel THEN btnLoginState is false`() {
        TestCase.assertFalse(
            vm.btnLoginState.value?.getContentIfNotHandled().handleOpt()
        )

    }

    @Test
    fun `WHEN onClickButton is called THEN btnLoginState is true`() =
        runTest {

            vm.onClickButtonLogin()
            val value = vm.btnLoginState.getOrAwaitValue()
            Assert.assertEquals(true, value.getContentIfNotHandled())
        }

    // StateResource - Section
    @Test
    fun `GIVEN the initial state of ViewModel THEN stateAccess is null`() {
        TestCase.assertNull(vm.stateAccess.value)
    }

    @Test
    fun `WHEN getAccessToken returns AccessToken THEN stateAccess_data returns AccessToken`() =
        runTest {
            val correctState = BaseState(
                isLoading = false,
                data = accessTokenMock,
                error = ""
            )

            coEvery { useCase(any()) } returns flow {
                emit(DataResource.Success(accessTokenMock))
            }

            vm.getAccessToken("")

            val value = vm.stateAccess.getOrAwaitValue()
            Assert.assertEquals(correctState, value)
        }

    @Test
    fun `WHEN getAccessToken returns IOException error THEN stateAccess_error returns network error`() =
        runTest {
            val correctState = BaseState(
                isLoading = false,
                data = null,
                error = ERROR_NETWORK
            )

            coEvery { useCase(any()) } returns flow {
                emit(DataResource.Error(ERROR_NETWORK))
            }

            vm.getAccessToken("")

            val value = vm.stateAccess.getOrAwaitValue()
            Assert.assertEquals(correctState, value)
        }

    @Test
    fun `WHEN getAccessToken returns HttpException error THEN stateAccess_error returns unexpected error `() =
        runTest {
            val correctState = BaseState(
                isLoading = false,
                data = null,
                error = UNEXPECTED_ERROR
            )

            coEvery { useCase(any()) } returns flow {
                emit(DataResource.Error(UNEXPECTED_ERROR))
            }

            vm.getAccessToken("")

            val value = vm.stateAccess.getOrAwaitValue()
            Assert.assertEquals(correctState, value)
        }

    @Test
    fun `GIVEN the initial state of ViewModel THEN stateToken is null`() {
        TestCase.assertNull(vm.stateToken.value)
    }

    @Test
    fun `WHEN getAccessToken returns AccessToken data THEN stateToken returns AccessToken `() =
        runTest {
            val correctState = BaseState(
                isLoading = false,
                data = accessTokenMock,
                error = ""
            )

            coEvery { useCase(any()) } returns flow {
                emit(DataResource.Success(data = accessTokenMock))
            }

            vm.getAccessToken("")

            val value = vm.stateToken.getOrAwaitValue()
            Assert.assertEquals(correctState.data, value)
        }
}