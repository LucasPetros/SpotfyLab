package com.lucas.petros.spotfylab.features.artists.data.repository

import androidx.paging.Pager
import com.lucas.petros.spotfylab.features.artists.domain.model.Album
import com.lucas.petros.spotfylab.features.artists.domain.model.Artist

interface ArtistsRepository {

    suspend fun getArtistsPage(auth: String): Pager<Int, Artist>
    suspend fun getAlbumsByArtistId(auth: String, id: String): Pager<Int, Album>

}