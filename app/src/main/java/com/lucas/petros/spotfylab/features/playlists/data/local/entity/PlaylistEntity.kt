package com.lucas.petros.spotfylab.features.playlists.data.local.entity


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Playlist")
data class PlaylistEntity(
    val collaborative: Boolean,
    val description: String,
    @PrimaryKey val id: String,
    val imageUrl: String,
    val name: String,
    val ownerName: String,
)