package com.lucas.petros.spotfylab.features.artists.domain.model


data class Artist(
    val genres: List<String>,
    val id: String,
    val imageUrl: String,
    val name: String,
)