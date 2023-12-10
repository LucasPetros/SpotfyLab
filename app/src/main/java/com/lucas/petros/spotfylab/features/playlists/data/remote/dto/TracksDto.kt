package com.lucas.petros.spotfylab.features.playlists.data.remote.dto


import com.google.gson.annotations.SerializedName

data class TracksDto(
    val href: String,
    val total: Int
)