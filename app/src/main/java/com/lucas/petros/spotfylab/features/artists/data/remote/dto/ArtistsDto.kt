package com.lucas.petros.spotfylab.features.artists.data.remote.dto


data class ArtistsDto(
    val href: String,
    val items: List<ItemDto>,
    val limit: Int,
    val next: Any,
    val offset: Int,
    val previous: Any,
    val total: Int
)