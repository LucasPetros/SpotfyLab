package com.lucas.petros.spotfylab.features.playlists.domain.model


import com.google.gson.annotations.SerializedName
import com.lucas.petros.commons.data.remote.dto.ExternalUrlsDto
import com.lucas.petros.commons.data.remote.dto.ImageDto

data class Playlist(
    val collaborative: Boolean,
    val description: String,
    val id: String,
    val imageUrl: String,
    val name: String,
    val ownerName: String,
)