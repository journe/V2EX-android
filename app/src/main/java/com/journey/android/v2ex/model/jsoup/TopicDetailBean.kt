package com.journey.android.v2ex.model.jsoup

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by journey on 2020/9/17.
 */
@Entity
data class TopicDetailBean(
  @PrimaryKey
  var id: Int = 0,
  var docString: String = ""
)