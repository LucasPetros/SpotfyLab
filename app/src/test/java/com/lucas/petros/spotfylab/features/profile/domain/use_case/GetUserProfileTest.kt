package com.lucas.petros.spotfylab.features.profile.domain.use_case

import app.cash.turbine.test
import com.lucas.petros.commons.data.DataResource
import com.lucas.petros.commons.extension.handleOpt
import com.lucas.petros.commons.utils.Prefs
import com.lucas.petros.network.NetworkConstants
import com.lucas.petros.spotfylab.features.mock.DataMock.userProfileMock
import com.lucas.petros.spotfylab.features.profile.data.repository.ProfileRepository
import com.lucas.petros.spotfylab.features.profile.domain.mapper.toEntity
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.coVerifySequence
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

@ExperimentalCoroutinesApi
class GetUserProfileTest {

    private val repository: ProfileRepository = mockk(relaxed = true)
    private val prefs: Prefs = mockk(relaxed = true)
    private val useCase = GetUserProfile(repository, prefs)

    //Resource loading - section
    @Test
    fun `WHEN getUserProfile THEN return resource loading and repository_getUserProfile and local data`() =
        runTest {

            val loading = DataResource.Loading(userProfileMock)
            coEvery { repository.getUserProfile(any()) } returns userProfileMock
            coEvery { repository.getInfoUser() } returns userProfileMock.toEntity()
            useCase("").test {
                awaitItem()
                Assert.assertEquals(
                    loading.data,
                    awaitItem().data
                )
                coVerify { repository.getInfoUser() }
                coVerify { repository.getUserProfile("") }
                cancelAndIgnoreRemainingEvents()
            }
        }

    //Resource success - section
    @Test
    fun `WHEN result is success THEN getUserProfile() return resource success with userProfile and save data`() =
        runTest {
            val success = DataResource.Success(userProfileMock)
            coEvery { repository.getUserProfile(any()) } returns userProfileMock
            coEvery { repository.getInfoUser() } returns userProfileMock.toEntity()

            useCase("")
                .test {
                    awaitItem()
                    Assert.assertEquals(success.data, awaitItem().data)
                    coVerifySequence {
                        repository.getInfoUser()
                        repository.getUserProfile("")
                        prefs.saveEncrypted(Prefs.KEY_USER_ID, userProfileMock.id.handleOpt())
                        repository.saveInfoUser(userProfileMock.toEntity())
                    }

                    cancelAndIgnoreRemainingEvents()
                }
        }

    //Resource error - section
    @Test
    fun `WHEN getUserProfile() returns HttpException THEN return Resource error with message Unexpected error`() =
        runTest {
            val unexpectedError = DataResource.Error(
                data = userProfileMock,
                message = NetworkConstants.UNEXPECTED_ERROR
            )
            val response = Response.error<Any>(404, "".toResponseBody(null))
            coEvery { repository.getUserProfile(any()) } throws HttpException(response)
            useCase("")
                .test {
                    awaitItem()
                    Assert.assertEquals(
                        unexpectedError.message,
                        awaitItem().message
                    )
                    cancelAndIgnoreRemainingEvents()
                }
            coVerify { repository.getUserProfile("") }
        }

    @Test
    fun `WHEN getUserProfile() returns IOException THEN return resource error with message network error`() =
        runTest {
            val networkError = DataResource.Error(
                data = userProfileMock,
                message = NetworkConstants.ERROR_NETWORK
            )
            coEvery { repository.getUserProfile(any()) } throws IOException()
            useCase("")
                .test {
                    awaitItem()
                    Assert.assertEquals(
                        networkError.message,
                        awaitItem().message
                    )
                    cancelAndIgnoreRemainingEvents()
                }
            coVerify { repository.getUserProfile("") }
        }
}