package com.lucas.petros.spotfylab.features.artists.domain.mapper

import com.lucas.petros.commons.data.domain.mapper.toDomain
import com.lucas.petros.spotfylab.features.artists.data.remote.dto.AlbumDto
import com.lucas.petros.spotfylab.features.artists.data.remote.dto.AlbumsDto
import com.lucas.petros.spotfylab.features.artists.data.remote.dto.ArtistDto
import com.lucas.petros.spotfylab.features.artists.data.remote.dto.ArtistsDto
import com.lucas.petros.spotfylab.features.artists.domain.model.Album
import com.lucas.petros.spotfylab.features.artists.domain.model.Albums
import com.lucas.petros.spotfylab.features.artists.domain.model.Artist
import com.lucas.petros.spotfylab.features.artists.domain.model.Artists

fun ArtistsDto.toDomain() = Artists(
    artists = items.map { it.toDomain() }
)

fun ArtistDto.toDomain() = Artist(
    id = id,
    imageUrl = images.map { it.toDomain() }[0].url,
    name = name
)

fun AlbumsDto.toDomain() = Albums(
    albums = items.map { it.toDomain() }
)

fun AlbumDto.toDomain() = Album(
    id = id,
    imageUrl = images.map { it.toDomain() }[0].url,
    releaseDate = releaseDate,
    name = name,
    releaseDatePrecision = releaseDatePrecision
)
