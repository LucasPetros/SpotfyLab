package com.lucas.petros.spotfylab.features.playlists.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.lucas.petros.spotfylab.data_source.PlaylistsDao
import com.lucas.petros.spotfylab.features.playlists.data.PlaylistsDataSource
import com.lucas.petros.spotfylab.features.playlists.data.remote.dto.PlaylistRequestDto
import com.lucas.petros.spotfylab.features.playlists.data.remote.service.PlaylistsApi
import com.lucas.petros.spotfylab.features.playlists.domain.mapper.toDto
import com.lucas.petros.spotfylab.features.playlists.domain.model.Playlist
import com.lucas.petros.spotfylab.features.playlists.domain.model.PlaylistRequest
import javax.inject.Inject

class IPlaylistsRepository @Inject constructor(
    private val api: PlaylistsApi,
    private val dao: PlaylistsDao
) :
    PlaylistsRepository {
    private val pageSize = 10
    private val prefetchDistance = 1
    override suspend fun getPlaylists(auth: String): Pager<Int, Playlist> =
        Pager(
            config = PagingConfig(pageSize = pageSize, prefetchDistance = prefetchDistance),
            pagingSourceFactory = { PlaylistsDataSource(auth, api, dao, pageSize) })

    override suspend fun createPlaylist(
        token: String,
        userId: String,
        playlistRequest: PlaylistRequest
    ) {
        api.createPlaylist("Bearer $token",userId,playlistRequest.toDto())
    }
}
