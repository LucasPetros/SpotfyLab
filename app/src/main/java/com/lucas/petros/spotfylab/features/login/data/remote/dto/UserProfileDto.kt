package com.lucas.petros.spotfylab.features.login.data.remote.dto


import com.google.gson.annotations.SerializedName
import com.lucas.petros.commons.data.remote.dto.ExternalUrlsDto
import com.lucas.petros.commons.data.remote.dto.FollowersDto
import com.lucas.petros.commons.data.remote.dto.ImageDto

data class UserProfileDto(
    val country: String,
    @SerializedName("display_name")
    val displayName: String,
    val email: String,
    @SerializedName("explicit_content")
    val explicitContent: ExplicitContentDto,
    @SerializedName("external_urls")
    val externalUrls: ExternalUrlsDto,
    val followers: FollowersDto,
    val href: String,
    val id: String,
    val images: List<ImageDto>,
    val product: String,
    val type: String,
    val uri: String
)