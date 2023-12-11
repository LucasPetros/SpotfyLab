package com.lucas.petros.spotfylab.features.artists.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Artist")
data class ArtistEntity(
    @PrimaryKey val id: String,
    val imageUrl: String,
    val name: String,
)