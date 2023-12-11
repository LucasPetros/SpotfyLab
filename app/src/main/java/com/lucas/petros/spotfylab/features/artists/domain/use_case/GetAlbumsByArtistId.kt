package com.lucas.petros.spotfylab.features.artists.domain.use_case

import androidx.paging.PagingData
import com.lucas.petros.spotfylab.features.artists.data.repository.ArtistsRepository
import com.lucas.petros.spotfylab.features.artists.domain.model.Album
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAlbumsByArtistId @Inject constructor(private val repository: ArtistsRepository) {

    suspend fun execute(auth: String, artistId: String): Flow<PagingData<Album>> {
        return repository.getAlbumsByArtistId(auth = auth, artistId).flow
    }
}