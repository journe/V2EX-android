package com.journey.android.v2ex.bean.api

import androidx.room.PrimaryKey

/**
 * Created by journey on 2018/8/8.
 */

/**
 * id : 1000
 * title : Google App Engine x MobileMe
 * url : http://www.v2ex.com/t/1000
 * content : 从现在开始，新上传到 V2EX 的头像将存储在 MobileMe iDisk 中。这是 V2EX 到目前为之所用到的第三个云。
 * content_rendered : 从现在开始，新上传到 V2EX 的头像将存储在 MobileMe iDisk 中。这是 V2EX 到目前为之所用到的第三个云。
 * replies : 14
 * member : {}
 * node : {}
 * created : 1280192329
 * last_modified : 1335004238
 * last_touched : 1280285385
 */
data class TopicsShowBean(
  @PrimaryKey
  var id: Int = 0,
  var title: String? = null,
  var url: String? = null,
  var content: String? = null,
  var content_rendered: String? = null,
  var replies: Int = 0,
  var member: MemberBean = MemberBean(),
  var node: NodeBean = NodeBean(),
  var created: Int = 0,
  var last_modified: Int = 0,
  var last_touched: Int = 0,
  var created_str: String = "",
  var subtles: MutableList<TopicShowSubtle>? = null

)