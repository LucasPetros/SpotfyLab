package com.lucas.petros.commons.extension

fun String.capitalize() = this.replaceFirstChar { it.uppercase() }