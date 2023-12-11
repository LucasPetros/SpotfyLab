package com.lucas.petros.spotfylab.features.playlists.data.remote.service

import com.lucas.petros.spotfylab.features.playlists.data.remote.dto.PlaylistRequestDto
import com.lucas.petros.spotfylab.features.playlists.data.remote.dto.PlaylistsDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface PlaylistsApi {

    @GET("v1/me/playlists")
    suspend fun getPlaylists(
        @Header("Authorization") auth: String,
        @Query("offset") page: Int,
        @Query("limit") limit: Int
    ): PlaylistsDto

    @POST("v1/users/{userId}/playlists")
    suspend fun createPlaylist(
        @Header("Authorization") token: String,
        @Path("userId") userId: String,
        @Body playlistRequest: PlaylistRequestDto
    )
}