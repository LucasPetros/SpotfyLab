package com.lucas.petros.spotfylab.features.login.data.repository

import com.lucas.petros.spotfylab.features.login.data.remote.service.LoginApi
import com.lucas.petros.spotfylab.features.login.domain.mapper.toDomain
import com.lucas.petros.spotfylab.features.login.domain.model.AccessToken
import javax.inject.Inject

class ILoginRepository @Inject constructor(
    private val api: LoginApi,
) :
    LoginRepository {
    override suspend fun getAccessToken(code: String?): AccessToken =
        api.getAccessToken(code = code).toDomain()

    override suspend fun getRefreshToken(refreshToken: String): AccessToken =
        api.getNewToken(refreshToken = refreshToken).toDomain()

}