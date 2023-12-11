package com.lucas.petros.spotfylab.features.profile.data.remote.service

import com.lucas.petros.spotfylab.features.profile.data.remote.dto.UserProfileDto
import retrofit2.http.GET
import retrofit2.http.Header

interface ProfileApi {

    @GET("v1/me")
    suspend fun getUserProfile(
        @Header("Authorization") auth: String,
    ): UserProfileDto
}