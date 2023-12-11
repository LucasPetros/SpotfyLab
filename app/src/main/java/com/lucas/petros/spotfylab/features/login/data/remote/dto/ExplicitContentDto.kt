package com.lucas.petros.spotfylab.features.login.data.remote.dto


import com.google.gson.annotations.SerializedName

data class ExplicitContentDto(
    @SerializedName("filter_enabled")
    val filterEnabled: Boolean,
    @SerializedName("filter_locked")
    val filterLocked: Boolean
)