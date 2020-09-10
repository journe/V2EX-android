package com.journey.android.v2ex.libs.imageEngine

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory
import com.bumptech.glide.load.engine.cache.LruResourceCache
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.module.AppGlideModule
import com.journey.android.v2ex.libs.imageEngine.GooglePhotoUrlLoader.Factory
import com.journey.android.v2ex.net.RetrofitRequest
import java.io.InputStream

@GlideModule
class GlideModule : AppGlideModule() {

  override fun applyOptions(
    context: Context,
    builder: GlideBuilder
  ) {
    super.applyOptions(context, builder)

    val maxMemory = Runtime.getRuntime()
        .maxMemory()
        .toInt()//获取系统分配给应用的总内存大小
    val memoryCacheSize = maxMemory / 8//设置图片内存缓存占用八分之一
    //设置内存缓存大小
    builder.setMemoryCache(LruResourceCache(memoryCacheSize.toLong()))

    context.let {
      val cacheDir = it.externalCacheDir//指定的是数据的缓存地址
      val diskCacheSize = 1024 * 1024 * 25//最多可以缓存30MB
      if (null != cacheDir) {
        //设置磁盘缓存大小
        builder.setDiskCache(DiskLruCacheFactory(cacheDir.path, "glide", diskCacheSize.toLong()))
      }
    }

    //设置BitmapPool缓存内存大小
    builder.setBitmapPool(LruBitmapPool(memoryCacheSize.toLong()))
  }

  override fun registerComponents(
    context: Context,
    glide: Glide,
    registry: Registry
  ) {
    registry.replace(
        GlideUrl::class.java, InputStream::class.java,
        OkHttpUrlLoader.Factory(RetrofitRequest.client)
    )
    registry.prepend(String::class.java, InputStream::class.java, Factory())
  }

  override fun isManifestParsingEnabled(): Boolean {
    return false
  }
}