package com.lucas.petros.spotfylab.features.login.domain.use_case

import com.lucas.petros.commons.data.DataResource
import com.lucas.petros.commons.utils.Prefs
import com.lucas.petros.network.NetworkConstants
import com.lucas.petros.network.extension.fetchErrorMessage
import com.lucas.petros.spotfylab.features.login.data.repository.LoginRepository
import com.lucas.petros.spotfylab.features.login.domain.model.AccessToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetRefreshToken @Inject constructor(
    private val repository: LoginRepository, private val prefs: Prefs,
) {

    operator fun invoke(refreshToken: String): Flow<DataResource<AccessToken>> =
        flow {
            try {
                emit(DataResource.Loading())
                val data = repository.getRefreshToken(refreshToken)
                saveData(data)
                emit(DataResource.Success(data = data))
            } catch (e: HttpException) {
                emit(DataResource.Error(e.fetchErrorMessage() ?: NetworkConstants.UNEXPECTED_ERROR))
            } catch (e: IOException) {
                emit(DataResource.Error(NetworkConstants.ERROR_NETWORK))
            }

        }

    private fun saveData(data: AccessToken) {
        prefs.saveEncrypted(Prefs.KEY_ACCESS_TOKEN, data.accessToken)
        prefs.saveEncrypted(Prefs.KEY_TOKEN_REFRESH, data.tokenRefresh)
    }
}