package com.lucas.petros.spotfylab.data_source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lucas.petros.spotfylab.features.artists.data.local.entity.AlbumEntity
import com.lucas.petros.spotfylab.features.artists.data.local.entity.ArtistEntity

@Dao
interface ArtistsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveArtists(artists: List<ArtistEntity>)

    @Query("SELECT * FROM Artist")
    suspend fun getArtists(): List<ArtistEntity>

    @Query("SELECT * FROM Artist LIMIT :pageSize OFFSET :offset")
    suspend fun getArtists(
        pageSize: Int,
        offset: Int
    ): List<ArtistEntity>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAlbums(artists: List<AlbumEntity>)

    @Query("SELECT * FROM Album LIMIT :pageSize OFFSET :offset")
    suspend fun getAlbums(
        pageSize: Int,
        offset: Int
    ): List<AlbumEntity>?
}