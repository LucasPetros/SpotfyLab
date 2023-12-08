package com.lucas.petros.spotfylab.data_source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lucas.petros.spotfylab.features.playlists.data.local.entity.PlaylistEntity

@Dao
interface PlaylistsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun savePlayLists(playlists: List<PlaylistEntity>)

    @Query("SELECT * FROM Playlist LIMIT :pageSize OFFSET :offset")
    suspend fun getPlayLists(
        pageSize: Int,
        offset: Int
    ): List<PlaylistEntity>?
}