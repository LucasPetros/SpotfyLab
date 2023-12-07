package com.lucas.petros.spotfylab.features.artists.data.remote.dto


import com.google.gson.annotations.SerializedName
import com.lucas.petros.commons.data.remote.dto.ExternalUrlsDto
import com.lucas.petros.commons.data.remote.dto.FollowersDto
import com.lucas.petros.commons.data.remote.dto.ImageDto

data class ItemDto(
    @SerializedName("external_urls")
    val externalUrls: ExternalUrlsDto,
    val followers: FollowersDto,
    val genres: List<String>,
    val href: String,
    val id: String,
    val images: List<ImageDto>,
    val name: String,
    val popularity: Int,
    val type: String,
    val uri: String
)