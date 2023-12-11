package com.lucas.petros.spotfylab.features.playlists.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.lucas.petros.commons.base.BaseState
import com.lucas.petros.commons.data.DataResource
import com.lucas.petros.commons.extension.handleOpt
import com.lucas.petros.commons.utils.Prefs
import com.lucas.petros.network.NetworkConstants
import com.lucas.petros.spotfylab.features.CoroutinesMainTestRule
import com.lucas.petros.spotfylab.features.mock.DataMock
import com.lucas.petros.spotfylab.features.playlists.domain.use_case.GetPlaylists
import com.lucas.petros.spotfylab.features.playlists.domain.use_case.PostCreatePlaylist
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
class PlaylistsViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = CoroutinesMainTestRule()

    private var useCasePlaylist = mockk<GetPlaylists>(relaxed = true)
    private var useCaseProfile = mockk<GetUserProfile>(relaxed = true)
    private var useCaseCreatePlaylist = mockk<PostCreatePlaylist>(relaxed = true)
    private var prefs = mockk<Prefs>(relaxed = true)
    private lateinit var vm: PlaylistsViewModel

    @Before
    fun prepare() {
        setupViewModel()
    }

    private fun setupViewModel() {
        vm = PlaylistsViewModel(
            useCasePlaylist, useCaseProfile, useCaseCreatePlaylist, prefs
        )
    }

    private fun getProfile(): Any? {
        val method: Method = vm.javaClass.getDeclaredMethod("getProfile")
        method.isAccessible = true
        return method.invoke(vm)
    }


    // Show Loading - Section
    @Test
    fun `GIVEN the initial state of ViewModel THEN showLoading is true`() {
        TestCase.assertTrue(vm.showLoading.value.handleOpt())
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
    fun `GIVEN the initial state of ViewModel THEN onClickButton is false`() {
        TestCase.assertFalse(
            vm.onClickButton.value?.getContentIfNotHandled().handleOpt()
        )

    }

    @Test
    fun `WHEN onClickButton is called THEN onClickButton is true`() =
        runTest {

            vm.onClickButton()
            val value = vm.onClickButton.getOrAwaitValue()
            Assert.assertEquals(true, value.getContentIfNotHandled())
        }

    // Init ViewModel - Section
    @Test
    fun `WHEN PlaylistsViewModel is instantiated THEN the getUserprofile is called`() {
        vm.run {
            verify { useCaseProfile.invoke(any()) }
        }
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
                data = DataMock.userProfileMock,
                error = ""
            )

            coEvery { useCaseProfile(any()) } returns flow {
                emit(DataResource.Success(DataMock.userProfileMock))
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
                data = DataMock.userProfileMock,
                error = NetworkConstants.ERROR_NETWORK
            )

            coEvery { useCaseProfile(any()) } returns flow {
                emit(
                    DataResource.Error(
                        data = DataMock.userProfileMock,
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
                data = DataMock.userProfileMock,
                error = NetworkConstants.UNEXPECTED_ERROR
            )

            coEvery { useCaseProfile(any()) } returns flow {
                emit(
                    DataResource.Error(
                        data = DataMock.userProfileMock,
                        message = NetworkConstants.UNEXPECTED_ERROR
                    )
                )
            }

            getProfile()

            val value = vm.stateUserProfile.getOrAwaitValue()
            Assert.assertEquals(correctState, value)
        }

    @Test
    fun `GIVEN the initial state of ViewModel THEN imageProfile is null`() {
        TestCase.assertNull(vm.imageProfile.value)
    }

    @Test
    fun `WHEN getProfile returns stateUserProfile_data THEN imageProfile receive UserProfile_image`() =
        runTest {
            val correctState = BaseState(
                isLoading = false,
                data = DataMock.userProfileMock,
                error = ""
            )

            coEvery { useCaseProfile(any()) } returns flow {
                emit(
                    DataResource.Error(
                        data = DataMock.userProfileMock,
                        message = NetworkConstants.UNEXPECTED_ERROR
                    )
                )
            }

            getProfile()

            val value = vm.imageProfile.getOrAwaitValue()
            Assert.assertEquals(correctState.data?.imageUrl, value)
        }

    // CreatePlaylist - Section

    @Test
    fun `GIVEN the initial state of ViewModel THEN stateCreatePlaylist is null`() {
        TestCase.assertNull(vm.stateCreatePlaylist.value)
    }

    @Test
    fun `WHEN createPlaylist returns Boolean THEN stateCreatePlaylist_data success returns True`() =
        runTest {
            val correctState = BaseState(
                isLoading = false,
                data = true,
                error = ""
            )

            coEvery { useCaseCreatePlaylist(any(), any(), any()) } returns flow {
                emit(DataResource.Success(true))
            }

            vm.createPlaylist("")

            val value = vm.stateCreatePlaylist.getOrAwaitValue()
            Assert.assertEquals(correctState, value)
        }

    @Test
    fun `WHEN createPlaylist returns IOException error THEN stateCreatePlaylist_error returns network error`() =
        runTest {
            val correctState = BaseState(
                isLoading = false,
                data = null,
                error = NetworkConstants.ERROR_NETWORK
            )

            coEvery { useCaseCreatePlaylist(any(), any(), any()) } returns flow {
                emit(
                    DataResource.Error(NetworkConstants.ERROR_NETWORK)
                )
            }

            vm.createPlaylist("")

            val value = vm.stateCreatePlaylist.getOrAwaitValue()
            Assert.assertEquals(correctState, value)
        }

    @Test
    fun `WHEN createPlaylist returns HttpException error THEN stateCreatePlaylist_error returns unexpected error`() =
        runTest {
            val correctState = BaseState(
                isLoading = false,
                data = null,
                error = NetworkConstants.UNEXPECTED_ERROR
            )

            coEvery { useCaseCreatePlaylist(any(), any(), any()) } returns flow {
                emit(DataResource.Error(NetworkConstants.UNEXPECTED_ERROR))
            }

            vm.createPlaylist("")

            val value = vm.stateCreatePlaylist.getOrAwaitValue()
            Assert.assertEquals(correctState, value)
        }


    @Test
    fun `WHEN createPlaylist returns stateUserProfile_data THEN isLoadingCreate receive stateCreatePlaylist_isLoading`() =
        runTest {
            val correctState = BaseState(
                isLoading = false,
                data = true,
                error = ""
            )

            coEvery { useCaseCreatePlaylist(any(), any(), any()) } returns flow {
                emit(
                    DataResource.Success(true)
                )
            }

            vm.createPlaylist("")

            val value = vm.isLoadingCreate.getOrAwaitValue()
            Assert.assertEquals(correctState.isLoading, value)
        }

    // State pagingList - Section
    @Test
    fun `GIVEN the initial state of ViewModel THEN pagingList is null`() {
        TestCase.assertNull(vm.pagingList.value)
    }
}