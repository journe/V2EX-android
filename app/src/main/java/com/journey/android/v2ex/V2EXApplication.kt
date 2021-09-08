package com.journey.android.v2ex

import android.app.Application
import androidx.lifecycle.ProcessLifecycleOwner
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.util.CoilUtils
import com.journey.android.v2ex.libs.ProcessLifecycleObserver
import com.journey.android.v2ex.utils.Utils
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import com.tencent.mmkv.MMKV
import com.zzhoujay.richtext.RichText
import dagger.hilt.android.HiltAndroidApp
import okhttp3.OkHttpClient

/**
 * Created by journey on 2018/1/26.
 */

@HiltAndroidApp
class V2EXApplication : Application(), ImageLoaderFactory {
    override fun onCreate() {
      super.onCreate()

      ProcessLifecycleOwner.get().lifecycle.addObserver(ProcessLifecycleObserver())

      Logger.addLogAdapter(object :
        AndroidLogAdapter(
          PrettyFormatStrategy.newBuilder().tag("V2EX").build()
        ) {
        override fun isLoggable(priority: Int, tag: String?): Boolean {
          return BuildConfig.DEBUG
        }
      })

      RichText.initCacheDir(this)

      Utils.init(this)

      MMKV.initialize(this)
    }

  override fun newImageLoader(): ImageLoader {
    return ImageLoader.Builder(applicationContext)
      .crossfade(true)
      .okHttpClient {
        OkHttpClient.Builder()
          .cache(CoilUtils.createDefaultCache(applicationContext))
          .build()
      }
      .build()  }
}

