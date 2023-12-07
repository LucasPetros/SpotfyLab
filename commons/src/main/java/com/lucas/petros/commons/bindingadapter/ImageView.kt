package com.lucas.petros.commons.bindingadapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.lucas.petros.commons.extension.handleOpt

/**
 * Download the image into the ImageView
 * @param url the string that contains a valid http address
 */
@BindingAdapter("loadUrlImage", "isCircle", requireAll = false)
fun ImageView.loadUrlImage(url: String?, isCircle: Boolean = false) {
    if (url == null) return
    if (isCircle) {
        Glide.with(context)
            .load(url)
            .circleCrop()
            .into(this)
    } else {
        Glide.with(context)
            .load(url)
            .into(this)
    }
}