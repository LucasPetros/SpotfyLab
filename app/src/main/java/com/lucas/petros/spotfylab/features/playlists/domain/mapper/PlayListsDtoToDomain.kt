package com.lucas.petros.spotfylab.features.playlists.domain.mapper

import com.lucas.petros.commons.data.domain.mapper.toDomain
import com.lucas.petros.commons.extension.handleOpt
import com.lucas.petros.spotfylab.features.playlists.data.local.entity.PlaylistEntity
import com.lucas.petros.spotfylab.features.playlists.data.remote.dto.PlaylistDto
import com.lucas.petros.spotfylab.features.playlists.data.remote.dto.PlaylistRequestDto
import com.lucas.petros.spotfylab.features.playlists.data.remote.dto.PlaylistsDto
import com.lucas.petros.spotfylab.features.playlists.domain.model.Playlist
import com.lucas.petros.spotfylab.features.playlists.domain.model.PlaylistRequest
import com.lucas.petros.spotfylab.features.playlists.domain.model.Playlists

fun PlaylistsDto.toDomain() = Playlists(
    playlists = items.map { it.toDomain() }
)

fun PlaylistDto.toDomain() = Playlist(
    collaborative = collaborative.handleOpt(),
    description = description.handleOpt(),
    id = id,
    imageUrl = images.map { it.toDomain().url.handleOpt() }[0],
    name = name.handleOpt(),
    ownerName = owner.displayName
)

fun Playlist.toEntity() = PlaylistEntity(
    collaborative = collaborative,
    description = description,
    id = id,
    imageUrl = imageUrl,
    name = name,
    ownerName = ownerName
)

fun PlaylistEntity.toDomain() = Playlist(
    collaborative = collaborative,
    description = description,
    id = id,
    imageUrl = imageUrl,
    name = name,
    ownerName = ownerName
)

fun PlaylistRequest.toDto()= PlaylistRequestDto(
    name = name
)