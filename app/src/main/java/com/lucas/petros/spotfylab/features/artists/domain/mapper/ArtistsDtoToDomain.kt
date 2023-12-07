package com.lucas.petros.spotfylab.features.artists.domain.mapper

import com.lucas.petros.commons.data.domain.mapper.toDomain
import com.lucas.petros.spotfylab.features.artists.data.remote.dto.ArtistsDto
import com.lucas.petros.spotfylab.features.artists.data.remote.dto.ItemDto
import com.lucas.petros.spotfylab.features.artists.domain.model.Artist
import com.lucas.petros.spotfylab.features.artists.domain.model.Artists

fun ArtistsDto.toDomain() = Artists(
    artists = items.map { it.toDomain() }
)

fun ItemDto.toDomain() = Artist(
    genres = genres,
    id = id,
    imageUrl = images.map { it.toDomain() }[0].url,
    name = name
)
