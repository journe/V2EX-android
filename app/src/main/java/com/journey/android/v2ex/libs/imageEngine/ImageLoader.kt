package tech.echoing.libs.imageEngine

import android.graphics.Bitmap
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.FutureTarget
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import tech.echoing.libs.R
import tech.echoing.libs.Utils
import tech.echoing.libs.extension.*
import java.io.File
import java.util.concurrent.ExecutionException

/**
 * Created by journey on 2020/4/27.
 */
object ImageLoader {

    fun loadCornerImage(
        imageView: ImageView,
        filePath: String?,
        radius: Float = 16f,
        isSquare: Boolean = false,
        widthRatio: Int = 0,
        heightRatio: Int = 0,
        isCentreCrop: Boolean = true
    ) {
        if (filePath?.startsWith("http") == false) {
            Glide.with(Utils.getContext())
                .load(filePath)
                .into(imageView)
            return
        }

        var imageUrl = filePath
        if (isSquare) {
            imageUrl = filePath?.fillW300H300Jpg()
        } else if (widthRatio > 0) {
            imageUrl = filePath?.resizeWidthHeight(widthRatio * 100, heightRatio * 100)
        } else {
            if (filePath?.contains("x-oss-process=image") == false) {
                imageUrl = filePath.lfitW1080Jpg()
            }
        }

        val transform = CornerTransform(Utils.getContext(), radius)
        val options = when(isCentreCrop){
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

    fun loadCornerImage(
        imageView: ImageView,
        uri: Uri?,
        radius: Float = 16f
    ) {
        val transform = CornerTransform(Utils.getContext(), radius)
        val options = RequestOptions()
            .placeholder(R.drawable.img_image_holder)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .transform(transform)
        Glide.with(Utils.getContext())
            .load(uri)
            .apply(options)
            .into(imageView)
    }

    fun loadCornerImage(
        imageView: ImageView,
        bitmap: Bitmap?,
        radius: Float
    ) {
        val transform = CornerTransform(Utils.getContext(), radius)
        val options = RequestOptions()
            .placeholder(R.drawable.img_image_holder)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .transform(transform)
        Glide.with(Utils.getContext())
            .load(bitmap)
            .apply(options)
            .into(imageView)
    }


    fun loadImage(
        imageView: ImageView,
        filePath: String?,
        isSquare: Boolean = false,
        widthRatio: Int = 0,
        heightRatio: Int = 0,
        needCompress: Boolean? = null,
        isCentreCrop: Boolean = true
    ) {
        if (filePath?.startsWith("http") == false || needCompress == false) {
            Glide.with(Utils.getContext())
                .load(filePath)
                .into(imageView)
            return
        }

        var imageUrl = filePath
        if (isSquare) {
            imageUrl = filePath?.fillW300H300Jpg()
        } else if (widthRatio > 0) {
            imageUrl = filePath?.resizeWidthHeight(widthRatio * 100, heightRatio * 100)
        } else {
            if (filePath?.contains("x-oss-process=image") == false) {
                imageUrl = filePath?.lfitW1080Jpg()
            }
        }
        val options = when(isCentreCrop){
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

    fun loadImageW320(
        imageView: ImageView,
        filePath: String?
    ) {
        var imageUrl = ""
        if (filePath?.contains("x-oss-process=image") == false) {
            imageUrl = filePath.lfitW320Jpg()
        }
        val options = RequestOptions()
            .placeholder(R.drawable.img_image_holder)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
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
        Glide.with(Utils.getContext()).clear(imageView)
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