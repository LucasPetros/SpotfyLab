package com.lucas.petros.spotfylab.features.login.domain.use_case

import com.lucas.petros.commons.data.DataResource
import com.lucas.petros.network.NetworkConstants
import com.lucas.petros.network.extension.fetchErrorMessage
import com.lucas.petros.spotfylab.features.login.data.repository.LoginRepository
import com.lucas.petros.spotfylab.features.login.domain.mapper.toDomain
import com.lucas.petros.spotfylab.features.login.domain.mapper.toEntity
import com.lucas.petros.spotfylab.features.login.domain.model.UserProfile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetUserProfile @Inject constructor(private val repository: LoginRepository) {

    operator fun invoke(auth: String): Flow<DataResource<UserProfile>> =
        flow {
            val localInfo = repository.getInfoUser()?.toDomain()
            try {
                emit(DataResource.Loading(data = localInfo))
                val data = repository.getUserProfile(auth)
                repository.saveInfoUser(data.toEntity())
                emit(DataResource.Success(data = data))
            } catch (e: HttpException) {
                emit(
                    DataResource.Error(
                        data = localInfo,
                        message = e.fetchErrorMessage() ?: NetworkConstants.UNEXPECTED_ERROR
                    )
                )
            } catch (e: IOException) {
                emit(DataResource.Error(data = localInfo, message = NetworkConstants.ERROR_NETWORK))
            }
        }
}