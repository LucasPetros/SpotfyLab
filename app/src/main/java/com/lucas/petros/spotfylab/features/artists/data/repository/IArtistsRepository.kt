package com.lucas.petros.spotfylab.features.artists.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.lucas.petros.spotfylab.features.artists.data.local.ArtistsDao
import com.lucas.petros.spotfylab.features.artists.data.AlbumsDataSource
import com.lucas.petros.spotfylab.features.artists.data.ArtistsDataSource
import com.lucas.petros.spotfylab.features.artists.data.remote.service.ArtistsApi
import com.lucas.petros.spotfylab.features.artists.domain.model.Album
import com.lucas.petros.spotfylab.features.artists.domain.model.Artist
import javax.inject.Inject

class IArtistsRepository @Inject constructor(
    private val api: ArtistsApi,
    private val artistsDao: ArtistsDao
) : ArtistsRepository {
    private val pageSize = 10
    private val prefetchDistance = 1

    override suspend fun getArtistsPage(auth: String): Pager<Int, Artist> = Pager(
        config = PagingConfig(pageSize = pageSize, prefetchDistance = prefetchDistance),
        pagingSourceFactory = { ArtistsDataSource(auth, api, artistsDao, pageSize) }
    )

    override suspend fun getAlbumsByArtistId(auth: String, id: String): Pager<Int, Album> = Pager(
        config = PagingConfig(pageSize = pageSize, prefetchDistance = prefetchDistance),
        pagingSourceFactory = { AlbumsDataSource(auth, id, api, artistsDao, pageSize) }
    )
}