package com.lucas.petros.spotfylab.features.artists.domain.mapper

import com.lucas.petros.commons.data.domain.mapper.toDomain
import com.lucas.petros.commons.extension.formatDate
import com.lucas.petros.commons.extension.handleOpt
import com.lucas.petros.spotfylab.features.artists.data.local.entity.AlbumEntity
import com.lucas.petros.spotfylab.features.artists.data.remote.dto.AlbumDto
import com.lucas.petros.spotfylab.features.artists.data.remote.dto.AlbumsDto
import com.lucas.petros.spotfylab.features.artists.domain.model.Album
import com.lucas.petros.spotfylab.features.artists.domain.model.Albums

fun AlbumsDto.toDomain(idArtist: String) = Albums(
    albums = items.map { it.toDomain(idArtist) }
)

fun AlbumDto.toDomain(idArtist:String) = Album(
    id = id,
    artistId= idArtist,
    imageUrl = images.map { it.toDomain() }[0].url.handleOpt(),
    releaseDate = releaseDate.handleOpt().formatDate().handleOpt(),
    name = name.handleOpt(),
    releaseDatePrecision = releaseDatePrecision.handleOpt()
)

fun AlbumEntity.toDomain(idArtist:String) = Album(
    id = id,
    imageUrl = imageUrl,
    releaseDate = releaseDate,
    name = name,
    releaseDatePrecision = releaseDatePrecision,
    artistId = idArtist
)

fun Album.toEntity(idArtist:String) = AlbumEntity(
    id = id,
    artistId = idArtist,
    imageUrl = imageUrl,
    releaseDate = releaseDate.handleOpt(),
    name = name.handleOpt(),
    releaseDatePrecision = releaseDatePrecision.handleOpt()
)