package com.journey.android.v2ex

import android.app.Application
import com.journey.android.v2ex.realm.MyMigration
import com.journey.android.v2ex.realm.MySchemaModule
import com.journey.android.v2ex.utils.Utils
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.zzhoujay.richtext.RichText
import io.realm.Realm
import io.realm.RealmConfiguration

/**
 * Created by journey on 2018/1/26.
 */

class V2exApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
        Logger.addLogAdapter(AndroidLogAdapter())
        RichText.initCacheDir(this)
        Realm.init(this)
        Utils.init(this)
        val config = RealmConfiguration.Builder()
            .name("v2ex.realm")
            .schemaVersion(1)
            .migration(MyMigration())
            .build()
        Realm.setDefaultConfiguration(config)
    }
    companion object {
        @JvmStatic
        lateinit var instance: V2exApplication
    }
}

