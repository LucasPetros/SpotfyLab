package com.lucas.petros.spotfylab.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lucas.petros.spotfylab.features.artists.data.local.entity.AlbumEntity
import com.lucas.petros.spotfylab.features.artists.data.local.entity.ArtistEntity
import com.lucas.petros.spotfylab.features.login.data.local.entity.UserProfileEntity

@Database(
    entities = [UserProfileEntity::class, ArtistEntity::class,AlbumEntity::class], version = 1
)

abstract class AppDatabase : RoomDatabase() {

    abstract fun loginDao(): LoginDao
    abstract fun artistsDao(): ArtistsDao

    companion object {
        const val DATABASE_NAME = "teste_db"
    }
}