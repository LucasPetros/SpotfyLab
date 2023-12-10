package com.lucas.petros.spotfylab.features.playlists.data.remote.dto


data class PlaylistsDto(
    val href: String,
    val items: List<PlaylistDto>,
    val limit: Int,
    val next: Any,
    val offset: Int,
    val previous: Any,
    val total: Int
)