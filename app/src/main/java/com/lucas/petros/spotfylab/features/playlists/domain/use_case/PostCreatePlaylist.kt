package com.lucas.petros.spotfylab.features.playlists.domain.use_case

import com.lucas.petros.commons.data.DataResource
import com.lucas.petros.network.NetworkConstants
import com.lucas.petros.network.extension.fetchErrorMessage
import com.lucas.petros.spotfylab.features.playlists.data.repository.PlaylistsRepository
import com.lucas.petros.spotfylab.features.playlists.domain.model.PlaylistRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class PostCreatePlaylist @Inject constructor(private val repository: PlaylistsRepository) {

    operator fun invoke(
        auth: String,
        userId: String,
        namePlaylist: String
    ): Flow<DataResource<Boolean>> =
        flow {
            try {
                emit(DataResource.Loading())
                val playlistRequest = PlaylistRequest(name = namePlaylist)
                repository.createPlaylist(auth, userId, playlistRequest)
                emit(DataResource.Success(data = true))

            } catch (e: HttpException) {
                emit(DataResource.Error(e.fetchErrorMessage() ?: NetworkConstants.UNEXPECTED_ERROR))
            } catch (e: IOException) {
                emit(DataResource.Error(NetworkConstants.ERROR_NETWORK))
            }
        }
}