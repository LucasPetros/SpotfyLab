package com.lucas.petros.spotfylab.features.artists.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.lucas.petros.commons.extension.handleOpt
import com.lucas.petros.spotfylab.data_source.ArtistsDao
import com.lucas.petros.spotfylab.features.artists.data.remote.service.ArtistsApi
import com.lucas.petros.spotfylab.features.artists.domain.mapper.toDomain
import com.lucas.petros.spotfylab.features.artists.domain.mapper.toEntity
import com.lucas.petros.spotfylab.features.artists.domain.model.Artist
import java.io.IOException

class ArtistsDataSource(
    private val auth: String,
    private val api: ArtistsApi,
    private val dao: ArtistsDao,
    private val limit: Int
) : PagingSource<Int, Artist>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Artist> {
        val pageSize = limit
        val currentPage = params.key ?: 0

        val offset = currentPage * pageSize
        val localArtists = dao.getArtists(pageSize, offset)?.map { it.toDomain() }.handleOpt()
        return try {

            val artists = api.getArtists("Bearer $auth", offset, pageSize).toDomain().artists
            dao.saveArtists(artists.map { it.toEntity() })

            val nextPage = if (artists.isNotEmpty()) currentPage + 1 else null

            LoadResult.Page(data = artists, prevKey = null, nextKey = nextPage)
        }  catch (e: IOException) {
            val nextPage = if (localArtists.isNotEmpty()) currentPage + 1 else null
            LoadResult.Page(data = localArtists, prevKey = null, nextKey = nextPage)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Artist>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}