package com.lucas.petros.spotfylab.features.login.data.repository

import com.lucas.petros.spotfylab.features.profile.data.local.entity.UserProfileEntity
import com.lucas.petros.spotfylab.features.login.domain.model.AccessToken
import com.lucas.petros.spotfylab.features.profile.domain.model.UserProfile

interface LoginRepository {

    suspend fun getAccessToken(code: String?): AccessToken

    suspend fun getRefreshToken(refreshToken: String): AccessToken
}