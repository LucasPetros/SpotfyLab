package com.lucas.petros.spotfylab.features.artists.data.data_source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.lucas.petros.spotfylab.features.artists.data.remote.service.ArtistsApi
import com.lucas.petros.spotfylab.features.artists.domain.mapper.toDomain
import com.lucas.petros.spotfylab.features.artists.domain.model.Album
import com.lucas.petros.spotfylab.features.artists.domain.model.Artist

class AlbumsDataSource(
    private val auth:String,
    private val id:String,
    private val api: ArtistsApi,
    private val limit: Int
) : PagingSource<Int, Album>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Album> {
        return try {
            val pageSize = limit
            val currentPage = params.key ?: 0

            val offset = currentPage * pageSize

            val albums = api.getArtistAlbumsById("Bearer $auth",id,offset, pageSize).toDomain().albums

            val nextPage = if (albums.isNotEmpty()) currentPage + 1 else null

            LoadResult.Page(data = albums, prevKey = null, nextKey = nextPage)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Album>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}