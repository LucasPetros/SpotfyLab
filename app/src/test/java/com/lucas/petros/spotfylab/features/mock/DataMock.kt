package com.lucas.petros.spotfylab.features.mock

import com.lucas.petros.spotfylab.features.login.domain.model.AccessToken
import com.lucas.petros.spotfylab.features.profile.domain.model.UserProfile

object DataMock {

    val accessTokenMock = AccessToken(
        accessToken = "token",
        tokenType = "Bearer",
        expiresIn = 0,
        tokenRefresh = ""
    )

    val userProfileMock = UserProfile(
        id = null,
        displayName = "nameMock",
        imageUrl = ""
    )
}
