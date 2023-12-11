package com.lucas.petros.spotfylab.features.login.domain.use_case

import app.cash.turbine.test
import com.lucas.petros.commons.data.DataResource
import com.lucas.petros.commons.utils.Prefs
import com.lucas.petros.commons.utils.Prefs.Companion.KEY_ACCESS_TOKEN
import com.lucas.petros.commons.utils.Prefs.Companion.KEY_TOKEN_REFRESH
import com.lucas.petros.network.NetworkConstants.ERROR_NETWORK
import com.lucas.petros.network.NetworkConstants.UNEXPECTED_ERROR
import com.lucas.petros.spotfylab.features.login.data.repository.LoginRepository
import com.lucas.petros.spotfylab.features.login.domain.model.AccessToken
import com.lucas.petros.spotfylab.features.mock.DataMock.accessTokenMock
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
import java.lang.reflect.Method

@ExperimentalCoroutinesApi
class GetRefreshTokenTest {
    private val repository: LoginRepository = mockk(relaxed = true)
    private val prefs: Prefs = mockk(relaxed = true)
    private val useCase = GetRefreshToken(repository, prefs)

    private fun invokePrivateFunction(accessToken: AccessToken): Any? {
        val method: Method =
            useCase.javaClass.getDeclaredMethod("saveData", AccessToken::class.java)
        method.isAccessible = true
        return method.invoke(useCase, accessToken)
    }

    //Resource loading - section
    @Test
    fun `WHEN getRefreshToken THEN return resource loading and repository_getRefreshToken`() =
        runTest {
            val loading = DataResource.Loading<AccessToken>()
            coEvery { repository.getRefreshToken("") }
            useCase("").test {
                Assert.assertEquals(
                    loading.data,
                    awaitItem().data
                )
                coVerify { repository.getRefreshToken("") }
                cancelAndIgnoreRemainingEvents()
            }
        }

    //Resource success - section
    @Test
    fun `WHEN result is success THEN getRefreshToken() return resource success with accessToken and save data`() =
        runTest {
            val success = DataResource.Success(accessTokenMock)
            coEvery { repository.getRefreshToken(any()) } returns accessTokenMock

            useCase("")
                .test {
                    awaitItem()
                    Assert.assertEquals(success.data, awaitItem().data)
                    coVerify { invokePrivateFunction(accessTokenMock) }
                    coVerifySequence {
                        repository.getRefreshToken("")
                        prefs.saveEncrypted(KEY_ACCESS_TOKEN, accessTokenMock.accessToken)
                        prefs.saveEncrypted(KEY_TOKEN_REFRESH, accessTokenMock.tokenRefresh)
                    }
                    cancelAndIgnoreRemainingEvents()
                }
        }

    //Resource error - section
    @Test
    fun `WHEN getRefreshToken() returns HttpException THEN return Resource error with message Unexpected error`() =
        runTest {
            val unexpectedError = DataResource.Error<AccessToken>(UNEXPECTED_ERROR)
            val response = Response.error<Any>(404, "".toResponseBody(null))
            coEvery { repository.getRefreshToken("") } throws HttpException(response)
            useCase("")
                .test {
                    awaitItem()
                    Assert.assertEquals(
                        unexpectedError.message,
                        awaitItem().message
                    )
                    cancelAndIgnoreRemainingEvents()
                }
            coVerify { repository.getRefreshToken("") }
        }

    @Test
    fun `WHEN getRefreshToken() returns IOException THEN return resource error with message network error`() =
        runTest {
            val networkError = DataResource.Error<AccessToken>(ERROR_NETWORK)
            coEvery { repository.getRefreshToken("") } throws IOException()
            useCase("")
                .test {
                    awaitItem()
                    Assert.assertEquals(
                        networkError.message,
                        awaitItem().message
                    )
                    cancelAndIgnoreRemainingEvents()
                }
            coVerify { repository.getRefreshToken("") }
        }
}