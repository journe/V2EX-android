package com.journey.android.v2ex.libs

import com.journey.android.v2ex.BuildConfig

object UpdateInfo {

    var hasNewVersion: Boolean = false
        private set
    var isRecommend: Boolean = false
        private set

    fun parseVersionData(info: VersionInfo) {
        val currentVersion = BuildConfig.VERSION_CODE

        if (info.version <= currentVersion) {
            // no new version
            return
        }

        hasNewVersion = true
        isRecommend = info.recommend

//        RxBus.post(AppUpdateEvent())
    }

    data class VersionInfo(
            val version: Int,
            val recommend: Boolean
    )
}
