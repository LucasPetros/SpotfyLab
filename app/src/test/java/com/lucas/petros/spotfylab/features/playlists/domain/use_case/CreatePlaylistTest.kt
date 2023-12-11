package com.lucas.petros.spotfylab.features.playlists.domain.use_case

import app.cash.turbine.test
import com.lucas.petros.commons.data.DataResource
import com.lucas.petros.network.NetworkConstants
import com.lucas.petros.spotfylab.features.playlists.data.repository.PlaylistsRepository
import io.mockk.coEvery
import io.mockk.coVerify
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
class CreatePlaylistTest {

    private val repository: PlaylistsRepository = mockk(relaxed = true)
    private val useCase = PostCreatePlaylist(repository)

    //Resource loading - section
    @Test
    fun `WHEN createPlaylist() THEN return resource loading and repository_postCreatePlaylist`() =
        runTest {
            val loading = DataResource.Loading<Boolean>()
            coEvery { repository.createPlaylist(any(), any(), any()) }
            useCase("", "", "").test {
                Assert.assertEquals(
                    loading.data,
                    awaitItem().data
                )
                coVerify { repository.createPlaylist(any(), any(), any()) }
                cancelAndIgnoreRemainingEvents()
            }
        }

    //Resource success - section
    @Test
    fun `WHEN result is success THEN createPlaylist() return resource success with playlist`() =
        runTest {
            val success = DataResource.Success(true)
            coEvery { repository.createPlaylist(any(), any(), any()) } returns Unit

            useCase("", "", "")
                .test {
                    awaitItem()
                    Assert.assertEquals(success.data, awaitItem().data)

                    cancelAndIgnoreRemainingEvents()
                }
            coVerify {
                repository.createPlaylist(any(), any(), any())
            }
        }

    //Resource error - section
    @Test
    fun `WHEN createPlaylist() returns HttpException THEN return Resource error with message Unexpected error`() =
        runTest {
            val unexpectedError = DataResource.Error<Boolean>(NetworkConstants.UNEXPECTED_ERROR)
            val response = Response.error<Any>(404, "".toResponseBody(null))
            coEvery {
                repository.createPlaylist(
                    any(),
                    any(),
                    any()
                )
            } throws HttpException(response)
            useCase("", "", "")
                .test {
                    awaitItem()
                    Assert.assertEquals(
                        unexpectedError.message,
                        awaitItem().message
                    )
                    cancelAndIgnoreRemainingEvents()
                }
            coVerify { repository.createPlaylist(any(), any(), any()) }
        }

    @Test
    fun `WHEN createPlaylist() returns IOException THEN return resource error with message network error`() =
        runTest {
            val networkError = DataResource.Error<Boolean>(NetworkConstants.ERROR_NETWORK)
            coEvery { repository.createPlaylist(any(), any(), any()) } throws IOException()
            useCase("", "", "")
                .test {
                    awaitItem()
                    Assert.assertEquals(
                        networkError.message,
                        awaitItem().message
                    )
                    cancelAndIgnoreRemainingEvents()
                }
            coVerify { repository.createPlaylist(any(), any(), any()) }
        }
}