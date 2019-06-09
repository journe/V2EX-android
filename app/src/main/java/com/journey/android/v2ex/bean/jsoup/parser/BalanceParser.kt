package com.journey.android.v2ex.bean.jsoup.parser

import com.journey.android.v2ex.bean.jsoup.BalanceBean
import com.orhanobut.logger.Logger
import org.jsoup.nodes.Document
import org.jsoup.select.Selector.select
import org.jsoup.select.Selector.selectFirst

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
object BalanceParser {

  @JvmStatic
  fun parseBalance(doc: Document): MutableList<BalanceBean> {
    val content = doc.body()
        .selectFirst("#Wrapper")
        .selectFirst(".content")
        .selectFirst(".box")
        .select("table")[1]
        .select("tr")
//    Logger.d(content.toString())
//        .selectFirst(".data")
//        .selectFirst("tbody")
//        .select("tr")
    val balanceBeans = mutableListOf<BalanceBean>()
    content.forEachIndexed { index, element ->
      if (index != 0) {
        val balanceBean = BalanceBean(
            element.select(".d")[0].text(),
            element.select(".d")[1].text(),
            element.select(".d")[2].text(),
            element.select(".d")[3].text()
        )
        balanceBeans.add(balanceBean)
      }
    }
    return balanceBeans
  }
}