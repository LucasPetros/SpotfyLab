package com.lucas.petros.commons.data.domain.mapper

import com.lucas.petros.commons.data.remote.dto.ImageDto
import com.lucas.petros.commons.data.domain.model.Image

fun ImageDto.toDomain() = Image(
    url = url
)