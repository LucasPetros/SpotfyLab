package com.lucas.petros.spotfylab.features.artists.data.remote.service

import com.lucas.petros.spotfylab.features.artists.data.remote.dto.ArtistsDto
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query


interface ArtistsApi {
    @GET("v1/me/top/artists")
    suspend fun getArtists(
        @Header("Authorization") auth: String,
        @Query("offset") page: Int,
        @Query("limit") limit: Int
    ): ArtistsDto

}



