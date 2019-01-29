package com.journey.android.v2ex.utils

import android.os.Build
import com.journey.android.v2ex.BuildConfig

/**
 * Created by journey on 2018/1/23.
 */

object Constants {
  const val BASE_URL = "https://www.v2ex.com"

  const val SITE_INFO = "/api/site/info.json"
  const val SITE_STATS = "/api/site/stats.json"

  const val NODES_ALL = "/api/nodes/all.json"
  const val NODES_SHOW = "/api/nodes/show.json"

  const val TOPICS_HOT = "/api/topics/hot.json"
  const val TOPICS_LATEST = "/api/topics/latest.json"
  const val TOPICS_SHOW = "/api/topics/show.json"
  const val REPLIES = "/api/replies/show.json"
  const val MEMBERS = "/api/members/show.json"

  const val SIGNIN = "/signin"
  const val MORE = "/more"
  const val TAB = "$BASE_URL/?tab="

  private const val USER_AGENT = "V2EX+/" + BuildConfig.VERSION_NAME
  val USER_AGENT_ANDROID = "$USER_AGENT (Android ${Build.VERSION.RELEASE})"

}

