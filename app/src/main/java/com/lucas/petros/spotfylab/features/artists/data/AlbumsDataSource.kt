package com.lucas.petros.spotfylab.features.artists.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.lucas.petros.commons.extension.handleOpt
import com.lucas.petros.spotfylab.features.artists.data.local.ArtistsDao
import com.lucas.petros.spotfylab.features.artists.data.remote.service.ArtistsApi
import com.lucas.petros.spotfylab.features.artists.domain.mapper.toDomain
import com.lucas.petros.spotfylab.features.artists.domain.mapper.toEntity
import com.lucas.petros.spotfylab.features.artists.domain.model.Album
import retrofit2.HttpException
import java.io.IOException

class AlbumsDataSource(
    private val auth: String,
    private val id: String,
    private val api: ArtistsApi,
    private val dao: ArtistsDao,
    private val limit: Int
) : PagingSource<Int, Album>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Album> {

        val pageSize = limit
        val currentPage = params.key ?: 0

        val offset = currentPage * pageSize

        val localAlbums = dao.getAlbums(id, pageSize, offset)?.map { it.toDomain(id) }.handleOpt()
        return try {

            val albums =
                api.getArtistAlbumsById("Bearer $auth", id, offset, pageSize).toDomain(id).albums
            dao.deleteAlbums(localAlbums.map { it.toEntity(id).id })

            dao.saveAlbums(albums.map { it.toEntity(id) }.handleOpt())

            val nextPage = if (albums.isNotEmpty()) currentPage + 1 else null

            LoadResult.Page(data = albums, prevKey = null, nextKey = nextPage)
        } catch (e: IOException) {
            val nextPage = if (localAlbums.isNotEmpty()) currentPage + 1 else null
            LoadResult.Page(data = localAlbums, prevKey = null, nextKey = nextPage)
        } catch (e: HttpException) {
            val nextPage = if (localAlbums.isNotEmpty()) currentPage + 1 else null
            LoadResult.Page(data = localAlbums, prevKey = null, nextKey = nextPage)
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