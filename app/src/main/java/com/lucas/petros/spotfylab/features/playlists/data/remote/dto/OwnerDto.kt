package com.lucas.petros.spotfylab.features.playlists.data.remote.dto


import com.google.gson.annotations.SerializedName
import com.lucas.petros.commons.data.remote.dto.ExternalUrlsDto

data class OwnerDto(
    @SerializedName("display_name")
    val displayName: String,
    @SerializedName("external_urls")
    val externalUrls: ExternalUrlsDto,
    val href: String,
    val id: String,
    val type: String,
    val uri: String
)