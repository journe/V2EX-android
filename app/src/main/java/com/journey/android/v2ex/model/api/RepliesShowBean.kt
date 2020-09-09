package com.journey.android.v2ex.model.api

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by journey on 2018/8/8.
 */

/**
 * id : 6236603
 * topic_id : 493298
 * member_id : 1
 * content : 很高兴看到 v2ex 又回来了，等了你半天发第一贴了，憋死我了。
 * content_rendered : 很高兴看到 v2ex 又回来了，等了你半天发第一贴了，憋死我了。<br></br><br></br>nice work~
 * member : {""}
 * created : 1272207477
 * last_modified : 1335092176
 */
@Entity
data class RepliesShowBean(
  @PrimaryKey
  var id: Int = 0,
  var topic_id: Int = 0,
  var member_id: Int = 0,
  var content: String? = null,
  var content_rendered: String? = null,
  var member: MemberBean = MemberBean(),
  var created: Int = 0,
  var created_str: String = "",
  var last_modified: Int = 0,
  var floor: Int = 0
)


