package com.lucas.petros.spotfylab.features.artists.domain.use_case

import androidx.paging.PagingData
import com.lucas.petros.spotfylab.features.artists.data.repository.ArtistsRepository
import com.lucas.petros.spotfylab.features.artists.domain.model.Artist
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetArtistsTopUser @Inject constructor(private val repository: ArtistsRepository) {

    suspend fun execute(auth: String): Flow<PagingData<Artist>> {
        return repository.getArtistsPage(auth = auth).flow
    }
}
