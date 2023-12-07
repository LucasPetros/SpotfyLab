package com.lucas.petros.spotfylab.features.login.domain.mapper

import com.lucas.petros.commons.extension.handleOpt
import com.lucas.petros.spotfylab.features.login.data.remote.dto.AccessTokenDto
import com.lucas.petros.spotfylab.features.login.domain.model.AccessToken

fun AccessTokenDto.toDomain() = AccessToken(
    accessToken = accessToken.handleOpt(),
    tokenType = tokenType.handleOpt(),
    expiresIn = expiresIn,
    tokenRefresh = tokenRefresh.handleOpt()
)