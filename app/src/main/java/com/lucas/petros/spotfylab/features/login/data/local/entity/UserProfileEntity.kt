package com.lucas.petros.spotfylab.features.login.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "UserProfile")
data class UserProfileEntity(
    @PrimaryKey val name: String,
    val imageUrl: String,
)