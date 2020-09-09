package com.journey.android.v2ex.model

import androidx.room.PrimaryKey
import com.journey.android.v2ex.model.api.TopicsListItemBean

/**
 * Created by journey on 2019-10-18.
 */
data class TabCache(
  @PrimaryKey
  var tabName: String = "",
  var topicsList: List<TopicsListItemBean> = emptyList()
)