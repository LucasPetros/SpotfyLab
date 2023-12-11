package com.lucas.petros.spotfylab.features.profile.data.repository

import com.lucas.petros.spotfylab.features.profile.data.local.ProfileDao
import com.lucas.petros.spotfylab.features.profile.data.local.entity.UserProfileEntity
import com.lucas.petros.spotfylab.features.profile.data.remote.service.ProfileApi
import com.lucas.petros.spotfylab.features.profile.domain.mapper.toDomain
import com.lucas.petros.spotfylab.features.profile.domain.model.UserProfile
import javax.inject.Inject

class IProfileRepository @Inject constructor(
    private val api: ProfileApi,
    private val profileDao: ProfileDao
) : ProfileRepository {
    override suspend fun getUserProfile(auth: String): UserProfile =
        api.getUserProfile("Bearer $auth").toDomain()

    override suspend fun saveInfoUser(userProfile: UserProfileEntity) {
        profileDao.saveInfoUser(userProfile)
    }

    override suspend fun getInfoUser(): UserProfileEntity =
        profileDao.getInfoUser()
}