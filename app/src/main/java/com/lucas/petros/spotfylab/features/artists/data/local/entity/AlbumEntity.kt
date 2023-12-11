package com.lucas.petros.spotfylab.features.artists.data.local.entity


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "Album"
)
data class AlbumEntity(
    @PrimaryKey val id: String,
    val artistId: String,
    val imageUrl: String,
    val name: String,
    val releaseDate: String,
    val releaseDatePrecision: String,
)