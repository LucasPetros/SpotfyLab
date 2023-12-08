package com.lucas.petros.commons.extension

import java.text.SimpleDateFormat
import java.util.Locale

fun String.capitalize() = this.replaceFirstChar { it.uppercase() }

fun String.formatDate(): String {
    return try {
        val data = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(this)
        val formatDesired = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        if (data != null){
            formatDesired.format(data).handleOpt()
        } else ""
    } catch (e: Exception) {
        e.printStackTrace()
        ""
    }
}