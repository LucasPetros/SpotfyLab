package com.lucas.petros.spotfylab.features.playlists.domain.use_case

import androidx.paging.PagingData
import com.lucas.petros.spotfylab.features.playlists.data.repository.PlaylistsRepository
import com.lucas.petros.spotfylab.features.playlists.domain.model.Playlist
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPlaylists @Inject constructor(private val repository: PlaylistsRepository) {

    suspend fun execute(auth: String): Flow<PagingData<Playlist>> {
        return repository.getPlaylists(auth = auth).flow
    }
}