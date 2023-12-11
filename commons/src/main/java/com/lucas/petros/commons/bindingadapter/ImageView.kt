package com.lucas.petros.commons.bindingadapter

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

/**
 * Download the image into the ImageView
 * @param url the string that contains a valid http address
 */
@BindingAdapter("loadUrlImage", "isCircle", "imageDefault", requireAll = false)
fun ImageView.loadUrlImage(url: String?, isCircle: Boolean = false, imageDefault: Drawable? = null) {
    if (url == null) return

    val request = Glide.with(context).load(url)

    if (isCircle) {
        request.circleCrop()
    }

    request.error(imageDefault)
        .transition(DrawableTransitionOptions.withCrossFade())
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .into(this)
}