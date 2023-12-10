package com.lucas.petros.spotfylab.features.login.data.repository

import com.lucas.petros.spotfylab.data.data_source.LoginDao
import com.lucas.petros.spotfylab.features.login.data.local.entity.UserProfileEntity
import com.lucas.petros.spotfylab.features.login.data.remote.service.LoginApi
import com.lucas.petros.spotfylab.features.login.domain.mapper.toDomain
import com.lucas.petros.spotfylab.features.login.domain.model.AccessToken
import com.lucas.petros.spotfylab.features.login.domain.model.UserProfile
import javax.inject.Inject

class ILoginRepository @Inject constructor(
    private val api: LoginApi,
    private val loginDao: LoginDao
) :
    LoginRepository {
    override suspend fun getAccessToken(code: String?): AccessToken =
        api.getAccessToken(code = code).toDomain()

    override suspend fun getRefreshToken(refreshToken: String): AccessToken =
        api.getNewToken(refreshToken = refreshToken).toDomain()

    override suspend fun getUserProfile(auth: String): UserProfile =
        api.getUserProfile("Bearer $auth").toDomain()

    override suspend fun saveInfoUser(userProfile: UserProfileEntity) {
        loginDao.saveInfoUser(userProfile)
    }

    override suspend fun getInfoUser(): UserProfileEntity =
        loginDao.getInfoUser()

}