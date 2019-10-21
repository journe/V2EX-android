package com.journey.android.v2ex.bean

import com.journey.android.v2ex.bean.api.TopicsListItemBean
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * Created by journey on 2019-10-18.
 */
open class TabCache(
  @PrimaryKey
  var tabName: String = "",
  var topicsList: RealmList<TopicsListItemBean> = RealmList()
) : RealmObject() {

}