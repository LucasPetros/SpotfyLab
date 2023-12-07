package com.lucas.petros.spotfylab.features.login.data.remote.dto

import com.google.gson.annotations.SerializedName

data class AccessTokenDto(
    @SerializedName("access_token")
    val accessToken: String?,
    @SerializedName("token_type")
    val tokenType: String?,
    @SerializedName("expires_in")
    val expiresIn: Int,
    @SerializedName("refresh_token")
    val tokenRefresh: String?
)
