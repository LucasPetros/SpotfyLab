package com.lucas.petros.spotfylab.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lucas.petros.spotfylab.features.artists.data.local.ArtistsDao
import com.lucas.petros.spotfylab.features.artists.data.local.entity.AlbumEntity
import com.lucas.petros.spotfylab.features.artists.data.local.entity.ArtistEntity
import com.lucas.petros.spotfylab.features.playlists.data.local.PlaylistsDao
import com.lucas.petros.spotfylab.features.profile.data.local.entity.UserProfileEntity
import com.lucas.petros.spotfylab.features.playlists.data.local.entity.PlaylistEntity
import com.lucas.petros.spotfylab.features.profile.data.local.ProfileDao

@Database(
    entities = [UserProfileEntity::class, ArtistEntity::class, AlbumEntity::class, PlaylistEntity::class],
    version = 1
)

abstract class AppDatabase : RoomDatabase() {

    abstract fun profileDao(): ProfileDao
    abstract fun artistsDao(): ArtistsDao
    abstract fun playlistsDao(): PlaylistsDao

    companion object {
        const val DATABASE_NAME = "xxx_db"
    }
}