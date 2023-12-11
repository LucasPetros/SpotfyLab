package com.lucas.petros.spotfylab.features.login.domain.use_case

import com.lucas.petros.commons.data.DataResource
import com.lucas.petros.commons.utils.Prefs
import com.lucas.petros.commons.utils.Prefs.Companion.KEY_ACCESS_TOKEN
import com.lucas.petros.commons.utils.Prefs.Companion.KEY_TOKEN_REFRESH
import com.lucas.petros.network.NetworkConstants.ERROR_NETWORK
import com.lucas.petros.network.NetworkConstants.UNEXPECTED_ERROR
import com.lucas.petros.network.extension.fetchErrorMessage
import com.lucas.petros.spotfylab.features.login.data.repository.LoginRepository
import com.lucas.petros.spotfylab.features.login.domain.model.AccessToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetAuthorizationRequest @Inject constructor(
    private val repository: LoginRepository,
    private val prefs: Prefs
) {

    operator fun invoke(code: String): Flow<DataResource<AccessToken>> =
        flow {
            try {
                emit(DataResource.Loading())
                val data = repository.getAccessToken(code)
                emit(DataResource.Success(data = data))
                saveData(data)
            } catch (e: HttpException) {
                emit(DataResource.Error(e.fetchErrorMessage() ?: UNEXPECTED_ERROR))
            } catch (e: IOException) {
                emit(DataResource.Error(ERROR_NETWORK))
            }
        }

    private fun saveData(data: AccessToken) {
        if (data.accessToken.isNotBlank()) {
            prefs.saveEncrypted(KEY_ACCESS_TOKEN, data.accessToken)
            prefs.saveEncrypted(KEY_TOKEN_REFRESH, data.tokenRefresh)
        }
    }
}