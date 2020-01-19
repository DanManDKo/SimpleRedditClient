package com.reddit.presentation.common.databinding.adapters

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.reddit.presentation.di.module.GlideApp

/**
 * Created with Android Studio.
 * User: Danil Konovalenko
 * Date: 2020-01-19
 * Time: 15:56
 */
object ImageBindingAdapter {

    private const val tagId = -345013456

    @SuppressLint("CheckResult")
    @JvmStatic
    @BindingAdapter(value = [
        "android:img_uri",
        "android:img_placeholder",
        "android:img_fallback",
        "android:img_error",
        "android:img_animated_placeholder"
    ], requireAll = false)
    fun setImage(
        imageView: ImageView,
        uri: String?,
        placeHolder: Drawable?,
        fallback: Drawable?,
        error: Drawable?,
        animatedPlaceHolder: Boolean?
    ) {
        if (uri != null && uri == imageView.getTag(tagId)) {
            return
        }

        var circularProgressDrawable: CircularProgressDrawable? = null
        if (animatedPlaceHolder == true) {
            circularProgressDrawable = CircularProgressDrawable(imageView.context)
            circularProgressDrawable.strokeWidth = 5f
            circularProgressDrawable.centerRadius = 30f
            circularProgressDrawable.start()
        }
        imageView.setTag(tagId, uri)

        GlideApp
            .with(imageView.context)
            .load(uri)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .thumbnail(0.1f)
            .placeholder(circularProgressDrawable ?: placeHolder)
            .fallback(if (uri == null) null else fallback)
            .error(if (uri == null) null else error ?: fallback)
            .into(imageView)
    }

}