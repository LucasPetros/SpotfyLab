package com.lucas.petros.spotfylab.features.profile.data.repository

import com.lucas.petros.spotfylab.features.profile.data.local.entity.UserProfileEntity
import com.lucas.petros.spotfylab.features.profile.domain.model.UserProfile

interface ProfileRepository {
    suspend fun getUserProfile(auth: String): UserProfile
    suspend fun saveInfoUser(userProfile: UserProfileEntity)
    suspend fun getInfoUser(): UserProfileEntity?
}