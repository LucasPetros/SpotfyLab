package com.lucas.petros.spotfylab.features.playlists.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.lucas.petros.commons.extension.handleOpt
import com.lucas.petros.spotfylab.features.playlists.data.local.PlaylistsDao
import com.lucas.petros.spotfylab.features.playlists.data.remote.service.PlaylistsApi
import com.lucas.petros.spotfylab.features.playlists.domain.mapper.toDomain
import com.lucas.petros.spotfylab.features.playlists.domain.mapper.toEntity
import com.lucas.petros.spotfylab.features.playlists.domain.model.Playlist
import retrofit2.HttpException
import java.io.IOException

class PlaylistsDataSource(
    private val auth: String,
    private val api: PlaylistsApi,
    private val dao: PlaylistsDao,
    private val limit: Int
) : PagingSource<Int, Playlist>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Playlist> {

        val pageSize = limit
        val currentPage = params.key ?: 0

        val offset = currentPage * pageSize

        val localPlaylists = dao.getPlayLists(pageSize, offset)?.map { it.toDomain() }.handleOpt()
        return try {

            val playlists = api.getPlaylists("Bearer $auth", offset, pageSize).toDomain().playlists
            dao.deletePlaylists(localPlaylists.map { it.toEntity().id }.handleOpt())
            dao.savePlayLists(playlists.map { it.toEntity() }.handleOpt())

            val nextPage = if (playlists.isNotEmpty()) currentPage + 1 else null
            LoadResult.Page(data = playlists, prevKey = null, nextKey = nextPage)
        } catch (e: IOException) {
            val nextPage = if (localPlaylists.isNotEmpty()) currentPage + 1 else null
            LoadResult.Page(data = localPlaylists, prevKey = null, nextKey = nextPage)
        } catch (e: HttpException) {
            val nextPage = if (localPlaylists.isNotEmpty()) currentPage + 1 else null
            LoadResult.Page(data = localPlaylists, prevKey = null, nextKey = nextPage)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Playlist>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}