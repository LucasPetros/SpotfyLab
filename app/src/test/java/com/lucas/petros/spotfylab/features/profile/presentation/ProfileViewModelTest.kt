package com.lucas.petros.spotfylab.features.profile.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.lucas.petros.commons.base.BaseState
import com.lucas.petros.commons.data.DataResource
import com.lucas.petros.commons.extension.handleOpt
import com.lucas.petros.commons.utils.Prefs
import com.lucas.petros.network.NetworkConstants
import com.lucas.petros.spotfylab.features.CoroutinesMainTestRule
import com.lucas.petros.spotfylab.features.mock.DataMock.userProfileMock
import com.lucas.petros.spotfylab.features.profile.domain.use_case.GetUserProfile
import getOrAwaitValue
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.lang.reflect.Method

@ExperimentalCoroutinesApi
class ProfileViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = CoroutinesMainTestRule()

    private var useCaseProfile = mockk<GetUserProfile>(relaxed = true)
    private var prefs = mockk<Prefs>(relaxed = true)
    private lateinit var vm: ProfileViewModel

    @Before
    fun prepare() {
        setupViewModel()
    }

    private fun setupViewModel() {
        vm = ProfileViewModel(
            useCaseProfile, prefs
        )
    }

    private fun getProfile(): Any? {
        val method: Method = vm.javaClass.getDeclaredMethod("getUserProfile")
        method.isAccessible = true
        return method.invoke(vm)
    }


    // Init ViewModel - Section
    @Test
    fun `WHEN ProfileViewModel is instantiated THEN the getUserprofile is called`() {
        vm.run {
            verify { useCaseProfile.invoke(any()) }
        }
    }

    // Show Loading - Section
    @Test
    fun `GIVEN the initial state of ViewModel THEN showLoading is false`() {
        TestCase.assertFalse(vm.showLoading.value.handleOpt())
    }

    @Test
    fun `WHEN getProfile is called and Resource Loading is issued THEN showLoading is true`() =
        runTest {

            coEvery { useCaseProfile(any()) } returns flow {
                emit(DataResource.Loading())
            }

            getProfile()

            val value = vm.showLoading.getOrAwaitValue()
            Assert.assertEquals(true, value)
        }

    @Test
    fun `WHEN getProfile is called and Resource Success is issued THEN showLoading is false`() =
        runTest {

            coEvery { useCaseProfile(any()) } returns flow {
                emit(DataResource.Success(userProfileMock))
            }

            getProfile()

            val value = vm.showLoading.getOrAwaitValue()

            Assert.assertEquals(false, value)
        }

    @Test
    fun `WHEN getProfile is called and Resource Error is issued THEN showLoading is false`() =
        runTest {
            coEvery { useCaseProfile(any()) } returns flow {
                emit(DataResource.Error("test error"))
            }

            getProfile()

            val value = vm.showLoading.getOrAwaitValue()

            Assert.assertEquals(false, value)
        }

    // State User Profile - Section

    @Test
    fun `GIVEN the initial state of ViewModel THEN stateUserProfile is null`() {
        TestCase.assertNull(vm.stateUserProfile.value)
    }

    @Test
    fun `WHEN getProfile returns UserProfile THEN stateUserProfile_data returns UserProfile`() =
        runTest {
            val correctState = BaseState(
                isLoading = false,
                data = userProfileMock,
                error = ""
            )

            coEvery { useCaseProfile(any()) } returns flow {
                emit(DataResource.Success(userProfileMock))
            }

            getProfile()

            val value = vm.stateUserProfile.getOrAwaitValue()
            Assert.assertEquals(correctState, value)
        }

    @Test
    fun `WHEN getProfile returns IOException error THEN stateUserProfile_error returns network error and local data`() =
        runTest {
            val correctState = BaseState(
                isLoading = false,
                data = userProfileMock,
                error = NetworkConstants.ERROR_NETWORK
            )

            coEvery { useCaseProfile(any()) } returns flow {
                emit(
                    DataResource.Error(
                        data = userProfileMock,
                        message = NetworkConstants.ERROR_NETWORK
                    )
                )
            }

            getProfile()

            val value = vm.stateUserProfile.getOrAwaitValue()
            Assert.assertEquals(correctState, value)
        }

    @Test
    fun `WHEN getProfile returns HttpException error THEN stateUserProfile_error returns unexpected error and local data`() =
        runTest {
            val correctState = BaseState(
                isLoading = false,
                data = userProfileMock,
                error = NetworkConstants.UNEXPECTED_ERROR
            )

            coEvery { useCaseProfile(any()) } returns flow {
                emit(
                    DataResource.Error(
                        data = userProfileMock,
                        message = NetworkConstants.UNEXPECTED_ERROR
                    )
                )
            }

            getProfile()

            val value = vm.stateUserProfile.getOrAwaitValue()
            Assert.assertEquals(correctState, value)
        }

    @Test
    fun `GIVEN the initial state of ViewModel THEN image is null`() {
        TestCase.assertNull(vm.image.value)
    }

    @Test
    fun `WHEN getProfile returns stateUserProfile_data THEN imageProfile receive UserProfile_image`() =
        runTest {
            val correctState = BaseState(
                isLoading = false,
                data = userProfileMock,
                error = ""
            )

            coEvery { useCaseProfile(any()) } returns flow {
                emit(
                    DataResource.Success(
                        data = userProfileMock,
                    )
                )
            }

            getProfile()

            val value = vm.image.getOrAwaitValue()
            Assert.assertEquals(correctState.data?.imageUrl, value)
        }

    @Test
    fun `GIVEN the initial state of ViewModel THEN username is null`() {
        TestCase.assertNull(vm.username.value)
    }

    @Test
    fun `WHEN getProfile returns stateUserProfile_data THEN username receive UserProfile_displayName`() =
        runTest {
            val correctState = BaseState(
                isLoading = false,
                data = userProfileMock,
                error = ""
            )

            coEvery { useCaseProfile(any()) } returns flow {
                emit(
                    DataResource.Success(
                        data = userProfileMock,
                    )
                )
            }

            getProfile()

            val value = vm.username.getOrAwaitValue()
            Assert.assertEquals(correctState.data?.displayName, value)
        }
}