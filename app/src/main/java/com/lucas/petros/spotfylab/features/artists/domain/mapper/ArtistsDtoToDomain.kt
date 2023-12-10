package com.lucas.petros.spotfylab.features.artists.domain.mapper

import com.lucas.petros.commons.data.domain.mapper.toDomain
import com.lucas.petros.commons.extension.handleOpt
import com.lucas.petros.spotfylab.features.artists.data.local.entity.ArtistEntity
import com.lucas.petros.spotfylab.features.artists.data.remote.dto.ArtistDto
import com.lucas.petros.spotfylab.features.artists.data.remote.dto.ArtistsDto
import com.lucas.petros.spotfylab.features.artists.domain.model.Artist
import com.lucas.petros.spotfylab.features.artists.domain.model.Artists

fun ArtistsDto.toDomain() = Artists(
    artists = items.map { it.toDomain() }
)

fun ArtistDto.toDomain() = Artist(
    id = id,
    imageUrl = images.firstOrNull()?.toDomain()?.url.handleOpt(),
    name = name.handleOpt()
)

fun Artist.toEntity() = ArtistEntity(
    id = id,
    imageUrl = imageUrl,
    name = name
)

fun ArtistEntity.toDomain() = Artist(
    id = id,
    imageUrl = imageUrl,
    name = name
)




