package com.lucas.petros.spotfylab.features.playlists.data.repository

import androidx.paging.Pager
import com.lucas.petros.spotfylab.features.playlists.data.remote.dto.PlaylistRequestDto
import com.lucas.petros.spotfylab.features.playlists.domain.model.Playlist
import com.lucas.petros.spotfylab.features.playlists.domain.model.PlaylistRequest
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Path

interface PlaylistsRepository {

    suspend fun getPlaylists(auth: String): Pager<Int, Playlist>
    suspend fun createPlaylist(token: String, userId: String, playlistRequest: PlaylistRequest)
}