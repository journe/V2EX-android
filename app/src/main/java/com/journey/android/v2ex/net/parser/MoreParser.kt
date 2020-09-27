package com.journey.android.v2ex.net.parser

import com.journey.android.v2ex.module.login.data.LoggedInUser
import com.orhanobut.logger.Logger
import org.jsoup.nodes.Document

/**
 * Selector选择器概述
 *  tagname: 通过标签查找元素，比如：a
 *  ns|tag: 通过标签在命名空间查找元素，比如：可以用 fb|name 语法来查找 <fb:name> 元素
 *  #id: 通过ID查找元素，比如：#logo
 *  .class: 通过class名称查找元素，比如：.masthead
 *  [attribute]: 利用属性查找元素，比如：[href]
 *  [^attr]: 利用属性名前缀来查找元素，比如：可以用[^data-] 来查找带有HTML5 Dataset属性的元素
 *  [attr=value]: 利用属性值来查找元素，比如：[width=500]
 *  [attr^=value], [attr$=value], [attr*=value]: 利用匹配属性值开头、结尾或包含属性值来查找元素，比如：[href*=/path/]
 *  [attr~=regex]: 利用属性值匹配正则表达式来查找元素，比如： img[src~=(?i)\.(png|jpe?g)]
 *   *: 这个符号将匹配所有元素
 */
object MoreParser {

  //右上角菜单超过两个div元素，说明正确获取到了登录状态
  @JvmStatic
  fun isLogin(doc: Document): Boolean {
    val content = doc.body()
        .selectFirst("#menu-body")
    val a = content.select(".cell")
    Logger.d(a.size)
    return a.size > 2
  }

  @JvmStatic
  fun getLoggedInUser(doc: Document): LoggedInUser {
    val content = doc.body()
        .selectFirst("#menu-entry")
        .selectFirst(".avatar mobile")
    return LoggedInUser(avatar = content.attr("src"), displayName = content.attr("alt"))
  }
}