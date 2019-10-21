package com.journey.android.v2ex.bean.api

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * Created by journey on 2018/8/8.
 */
open class NodeBean(
  @PrimaryKey
  var id: Int = 0,
  var name: String = "",
  var url: String? = null,
  var title: String? = null,
  var title_alternative: String? = null,
  var topics: Int = 0,
  var header: String? = null,
  var footer: String? = null,
  var avatar_large: String? = null,
  var stars: Int = 0,
  var root: Boolean = false,
  var parent_node_name: String? = null
) : RealmObject() {

  /**
   * id : 1
   * name : babel
   * url : http://www.v2ex.com/go/babel
   * title : Project Babel
   * title_alternative : Project Babel
   * topics : 1102
   * header : Project Babel - 帮助你在云平台上搭建自己的社区
   * footer : V2EX 基于 Project Babel 驱动。Project Babel 是用 Python 语言写成的，运行于 Google App Engine 云计算平台上的社区软件。Project Babel 当前开发分支 2.5。最新版本可以从 [GitHub](http://github.com/livid/v2ex) 获取。
   * avatar_large : //cdn.v2ex.com/navatar/c4ca/4238/1_large.png?m=1370299418
   * stars : 332
   * root : false
   * parent_node_name : v2ex
   */
}
