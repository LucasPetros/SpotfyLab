package com.lucas.petros.spotfylab.features.login.domain.mapper

import com.lucas.petros.commons.data.domain.mapper.toDomain
import com.lucas.petros.commons.extension.handleOpt
import com.lucas.petros.spotfylab.features.login.data.local.entity.UserProfileEntity
import com.lucas.petros.spotfylab.features.login.data.remote.dto.UserProfileDto
import com.lucas.petros.spotfylab.features.login.domain.model.UserProfile


fun UserProfileDto.toDomain() = UserProfile(
    displayName = displayName.handleOpt(),
    imageUrl = images.firstOrNull()?.toDomain()?.url.handleOpt(),
    id = id.handleOpt()
)

fun UserProfile.toEntity() = UserProfileEntity(
    name = displayName.handleOpt(),
    imageUrl = imageUrl.handleOpt(),


    )

fun UserProfileEntity.toDomain() = UserProfile(
    displayName = name.handleOpt(),
    imageUrl = imageUrl.handleOpt(),
)

