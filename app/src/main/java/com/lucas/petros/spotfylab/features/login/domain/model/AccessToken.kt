package com.lucas.petros.spotfylab.features.login.domain.model

data class AccessToken(
    val accessToken: String,
    val tokenType: String,
    val expiresIn: Int,
    val tokenRefresh: String
)
