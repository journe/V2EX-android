package com.journey.android.v2ex

import android.app.Application
import androidx.lifecycle.ViewModelProviders
import com.journey.android.v2ex.utils.Utils
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import com.zzhoujay.richtext.RichText
import dagger.hilt.android.HiltAndroidApp

/**
 * Created by journey on 2018/1/26.
 */

@HiltAndroidApp
class V2EXApplication : Application() {

  override fun onCreate() {
    super.onCreate()
    Logger.addLogAdapter(object :
        AndroidLogAdapter(
            PrettyFormatStrategy.newBuilder().tag("V2EX").build()) {
      override fun isLoggable(priority: Int, tag: String?): Boolean {
        return BuildConfig.DEBUG
      }
    })
    RichText.initCacheDir(this)
    Utils.init(this)
  }
}

