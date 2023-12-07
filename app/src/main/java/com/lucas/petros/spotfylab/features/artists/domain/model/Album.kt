package com.lucas.petros.spotfylab.features.artists.domain.model


import com.google.gson.annotations.SerializedName
import com.lucas.petros.commons.data.remote.dto.ExternalUrlsDto
import com.lucas.petros.commons.data.remote.dto.ImageDto

data class Album(
    val id: String,
    val imageUrl: String,
    val name: String,
    val releaseDate: String,
    val releaseDatePrecision: String,
)