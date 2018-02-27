package me.journey.android.v2ex.utils

/**
 * Created by journey on 2018/2/8.
 */

import android.content.Context
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class ImageLoader {

    companion object {
        fun displayImage(context: Context, uri: String, view: ImageView) {
            Glide.with(context).load(uri).into(view)
        }

        fun displayImage(view: View, uri: String, imageView: ImageView) {
            Glide.with(view).load(uri).into(imageView)
        }

        fun displayImage(view: View, uri: String, imageView: ImageView, placeholder: Int) {
            val options = RequestOptions()
            if (placeholder != 0) {
                options.placeholder(placeholder)
            }
            Glide.with(view).load(uri).apply(options).into(imageView)
        }

        fun displayImage(view: View, uri: String?, imageView: ImageView, placeholder: Int, radius: Int) {
            val options = RequestOptions()
            if (placeholder != 0) {
                options.placeholder(placeholder)
            }
            options.transform(GlideRoundTransform(view.context, radius))
            Glide.with(view).load("http:" + uri).apply(options).into(imageView)
        }
    }

}
