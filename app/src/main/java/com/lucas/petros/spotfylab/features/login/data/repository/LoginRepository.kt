package com.lucas.petros.spotfylab.features.login.data.repository

import com.lucas.petros.spotfylab.features.login.domain.model.AccessToken

interface LoginRepository {

    suspend fun getAccessToken(code: String?): AccessToken
    suspend fun getUserProfile(code: String?): String
}