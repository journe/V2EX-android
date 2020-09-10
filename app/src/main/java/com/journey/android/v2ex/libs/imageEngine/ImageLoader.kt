package com.journey.android.v2ex.libs.imageEngine

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.FutureTarget
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.journey.android.v2ex.R
import com.journey.android.v2ex.utils.Utils
import java.io.File

/**
 * Created by journey on 2020/4/27.
 */
object ImageLoader {
  fun loadCornerImage(
      imageView: ImageView,
      filePath: String?,
      radius: Float = 16f,
      isCentreCrop: Boolean = true
  ) {
    var imageUrl = filePath
    val transform = CornerTransform(Utils.getContext(), radius)
//        val transform = GlideRoundTransform(Utils.getContext(), 8)
    val options = when (isCentreCrop) {
        true -> RequestOptions()
            .placeholder(R.drawable.img_image_holder)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .transform(transform)
            .centerCrop()
      else -> RequestOptions()
          .placeholder(R.drawable.img_image_holder)
          .diskCacheStrategy(DiskCacheStrategy.ALL)
          .transform(transform)
    }
    Glide.with(Utils.getContext())
        .load(imageUrl)
        .apply(options)
        .into(imageView)
  }

  fun loadImage(
      imageView: ImageView,
      filePath: String?,
      isCentreCrop: Boolean = false
  ) {

    var imageUrl = filePath
    val options = when (isCentreCrop) {
        true -> RequestOptions()
            .placeholder(R.drawable.img_image_holder)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()
      else -> RequestOptions()
          .placeholder(R.drawable.img_image_holder)
          .diskCacheStrategy(DiskCacheStrategy.ALL)
    }
    Glide.with(Utils.getContext())
        .load(imageUrl)
        .apply(options)
        .into(imageView)
  }

  fun loadImage(
      imageView: ImageView,
      filePath: Int
  ) {
    Glide.with(Utils.getContext())
        .load(filePath)
        .into(imageView)
  }

  fun clear(imageView: ImageView) {
    Glide.with(Utils.getContext())
        .clear(imageView)
  }

  fun downLoadImage(url: String?): FutureTarget<File?>? {
    return Glide.with(Utils.getContext())
        .load(url)
        .downloadOnly(
            Target.SIZE_ORIGINAL,
            Target.SIZE_ORIGINAL
        )
  }
}