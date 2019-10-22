package com.journey.android.v2ex.bean.api

import io.realm.RealmObject

open class TopicShowSubtle : RealmObject() {
  var id: Int = 0
  var title: String = ""
  var content: String = ""
}