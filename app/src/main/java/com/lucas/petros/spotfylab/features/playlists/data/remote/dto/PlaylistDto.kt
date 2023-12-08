package com.lucas.petros.spotfylab.features.playlists.data.remote.dto


import com.google.gson.annotations.SerializedName
import com.lucas.petros.commons.data.remote.dto.ExternalUrlsDto
import com.lucas.petros.commons.data.remote.dto.ImageDto

data class PlaylistDto(
    val collaborative: Boolean,
    val description: String,
    @SerializedName("external_urls")
    val externalUrls: ExternalUrlsDto,
    val href: String,
    val id: String,
    val images: List<ImageDto>,
    val name: String,
    val owner: OwnerDto,
    @SerializedName("primary_color")
    val primaryColor: Any,
    val `public`: Boolean,
)