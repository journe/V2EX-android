package me.journey.android.v2ex

import android.app.Application
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.zzhoujay.richtext.RichText

/**
 * Created by journey on 2018/1/26.
 */

class V2exApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Logger.addLogAdapter(AndroidLogAdapter())
        RichText.initCacheDir(this)
    }
}

