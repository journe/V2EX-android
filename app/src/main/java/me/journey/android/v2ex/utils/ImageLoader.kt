package me.journey.android.v2ex.utils

/**
 * Created by journey on 2018/2/8.
 */

import android.content.Context
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide

class ImageLoader {

    companion object {
        fun displayImage(context: Context, uri: String, view: ImageView) {
            Glide.with(context).load(uri).into(view)
        }

        fun displayImage(view: View, uri: String, imageView: ImageView) {
            Glide.with(view).load(uri).into(imageView)
        }
    }

}
