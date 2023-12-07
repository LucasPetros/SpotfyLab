package com.lucas.petros.spotfylab.features.login.domain.model


import com.google.gson.annotations.SerializedName
import com.lucas.petros.commons.data.domain.model.Image
import com.lucas.petros.commons.data.remote.dto.ExternalUrlsDto
import com.lucas.petros.commons.data.remote.dto.FollowersDto
import com.lucas.petros.commons.data.remote.dto.ImageDto

data class UserProfile(
    val displayName: String,
    val imageUrl: String,
)