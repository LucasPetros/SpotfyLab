package com.lucas.petros.spotfylab.features.profile.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "UserProfile")
data class UserProfileEntity(
    @PrimaryKey val name: String,
    val imageUrl: String,
)