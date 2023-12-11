package com.lucas.petros.spotfylab.features.profile.domain.model


data class UserProfile(
    val id: String? = null,
    val displayName: String,
    val imageUrl: String,
)