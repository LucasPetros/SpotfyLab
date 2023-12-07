package com.lucas.petros.spotfylab.features.artists.data.remote.dto


import com.google.gson.annotations.SerializedName
import com.lucas.petros.commons.data.remote.dto.ExternalUrlsDto

data class ArtistAlbumDto(
    @SerializedName("external_urls")
    val externalUrls: ExternalUrlsDto,
    val href: String,
    val id: String,
    val name: String,
    val type: String,
    val uri: String
)