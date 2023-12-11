package com.lucas.petros.spotfylab.features.artists.presentation.artist_list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.lucas.petros.commons.base.BaseState
import com.lucas.petros.commons.data.DataResource
import com.lucas.petros.commons.extension.handleOpt
import com.lucas.petros.commons.utils.Prefs
import com.lucas.petros.network.NetworkConstants
import com.lucas.petros.spotfylab.features.CoroutinesMainTestRule
import com.lucas.petros.spotfylab.features.artists.domain.use_case.GetArtistsTopUser
import com.lucas.petros.spotfylab.features.login.domain.use_case.GetRefreshToken
import com.lucas.petros.spotfylab.features.mock.DataMock.accessTokenMock
import com.lucas.petros.spotfylab.features.mock.DataMock.userProfileMock
import com.lucas.petros.spotfylab.features.profile.domain.use_case.GetUserProfile
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
class ArtistsViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = CoroutinesMainTestRule()

    private var useCaseArtists = mockk<GetArtistsTopUser>(relaxed = true)
    private var useCaseNewToken = mockk<GetRefreshToken>(relaxed = true)
    private var useCaseProfile = mockk<GetUserProfile>(relaxed = true)
    private val prefs: Prefs = mockk(relaxed = true)
    private lateinit var vm: ArtistsViewModel

    @Before
    fun prepare() {
        setupViewModel()
    }

    private fun setupViewModel() {
        vm = ArtistsViewModel(useCaseArtists, useCaseNewToken, useCaseProfile, prefs)
    }

    // Show Loading - Section
    @Test
    fun `GIVEN the initial state of ViewModel THEN showLoading is false`() {
        TestCase.assertTrue(vm.showLoading.value.handleOpt())
    }


    @Test
    fun `WHEN fun showStopLoading is called THEN showLoading is false`() =
        runTest {

            vm.showStopLoading()
            val value = vm.showLoading.getOrAwaitValue()
            Assert.assertEquals(false, value)
        }

    // State Access - Section
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

            coEvery { useCaseNewToken(any()) } returns flow {
                emit(DataResource.Success(accessTokenMock))
            }

            vm.getRefreshToken()

            val value = vm.stateAccess.getOrAwaitValue()
            Assert.assertEquals(correctState, value)
        }

    @Test
    fun `WHEN getAccessToken returns IOException error THEN stateAccess_error returns network error`() =
        runTest {
            val correctState = BaseState(
                isLoading = false,
                data = null,
                error = NetworkConstants.ERROR_NETWORK
            )

            coEvery { useCaseNewToken(any()) } returns flow {
                emit(DataResource.Error(NetworkConstants.ERROR_NETWORK))
            }

            vm.getRefreshToken()

            val value = vm.stateAccess.getOrAwaitValue()
            Assert.assertEquals(correctState, value)
        }

    @Test
    fun `WHEN getAccessToken returns HttpException error THEN stateAccess_error returns unexpected error `() =
        runTest {
            val correctState = BaseState(
                isLoading = false,
                data = null,
                error = NetworkConstants.UNEXPECTED_ERROR
            )

            coEvery { useCaseNewToken(any()) } returns flow {
                emit(DataResource.Error(NetworkConstants.UNEXPECTED_ERROR))
            }

            vm.getRefreshToken()

            val value = vm.stateAccess.getOrAwaitValue()
            Assert.assertEquals(correctState, value)
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

            vm.getProfile("")

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

            vm.getProfile("")

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

            vm.getProfile("")

            val value = vm.stateUserProfile.getOrAwaitValue()
            Assert.assertEquals(correctState, value)
        }

    @Test
    fun `GIVEN the initial state of ViewModel THEN image is null`() {
        TestCase.assertNull(vm.image.value)
    }

    @Test
    fun `WHEN getProfile returns stateUserProfile_data THEN image receive UserProfile_image`() =
        runTest {
            val correctState = BaseState(
                isLoading = false,
                data = userProfileMock,
                error = ""
            )

            coEvery { useCaseProfile(any()) } returns flow {
                emit(
                    DataResource.Error(
                        data = userProfileMock,
                        message = NetworkConstants.UNEXPECTED_ERROR
                    )
                )
            }

            vm.getProfile("")

            val value = vm.image.getOrAwaitValue()
            Assert.assertEquals(correctState.data?.imageUrl, value)
        }

    // State pagingList - Section
    @Test
    fun `GIVEN the initial state of ViewModel THEN pagingList is null`() {
        TestCase.assertNull(vm.pagingList.value)
    }
}