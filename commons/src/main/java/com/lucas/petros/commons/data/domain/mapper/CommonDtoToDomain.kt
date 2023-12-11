package com.lucas.petros.commons.data.domain.mapper

import com.lucas.petros.commons.data.remote.dto.ImageDto
import com.lucas.petros.commons.data.domain.model.Image
import com.lucas.petros.commons.extension.handleOpt

fun ImageDto.toDomain() = Image(
    url = url.handleOpt()
)