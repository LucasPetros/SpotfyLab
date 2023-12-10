package com.lucas.petros.commons.bindingadapter

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

/**
 * Download the image into the ImageView
 * @param url the string that contains a valid http address
 */
@BindingAdapter("loadUrlImage", "isCircle", "imageDefault", requireAll = false)
fun ImageView.loadUrlImage(url: String?, isCircle: Boolean = false, imageDefault: Drawable? = null) {
    if (url == null) return
    if (isCircle) {
        Glide.with(context)
            .load(url)
            .circleCrop()
            .error(imageDefault)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(this)
    } else {
        Glide.with(context)
            .load(url)
            .error(imageDefault)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(this)
    }
}