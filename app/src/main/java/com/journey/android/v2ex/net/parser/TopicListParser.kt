package com.journey.android.v2ex.net.parser

import com.journey.android.v2ex.model.api.TopicsListItemBean
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
object TopicListParser {
	private val PATTERN_NUMBERS = "\\d+".toRegex()

	/**
	 * <tr>
	 *  <td width="24" valign="top" align="center"><a href="/member/zshstc">
	 *    <img src="//cdn.v2ex.com/avatar/c5b0/3cd6/193770_normal.png?m=1538493394" class="avatar" border="0" align="default" style="max-width: 24px; max-height: 24px;"></a>
	 *  </td>
	 *  <td width="10"></td>
	 *  <td width="auto" valign="middle">
	 *    <span class="small fade">
	 *      <a class="node" href="/go/macos">macOS</a> &nbsp;•&nbsp;
	 *      <strong>
	 *        <a href="/member/zshstc">zshstc</a>
	 *      </strong>
	 *    </span>
	 *   <div class="sep5"></div>
	 *   <span class="item_title"><a href="/t/530863#reply14">请教下 Mac OS 下如何隐藏多余的 wifi?</a></span>
	 *   <div class="sep5"></div>
	 *   <span class="small fade">2 天前 &nbsp;•&nbsp; 最后回复 <strong><a href="/member/NeoChen">NeoChen</a></strong></span>
	 *  </td>
	 *  <td width="70" align="right" valign="middle"> <a href="/t/530863#reply14" class="count_livid">14</a> </td>
	 * </tr>
	 */

	@JvmStatic
	fun parseTopicList(doc: Document): List<TopicsListItemBean> {
		val content = doc.body()
			.selectFirst("#Wrapper")
			.selectFirst(".content")
			.selectFirst(".box")
			.select(".cell.item")
			.select("table")
			.select("tbody")
			.select("tr")
		val topicList: MutableList<TopicsListItemBean> = mutableListOf()
		for (element in content) {
//      Logger.d(element.toString())
			val td = element.select("td")
			val topicListBean = TopicsListItemBean().apply {
				memberName = td[0].select("a")
					.attr("href")
					.substringAfterLast("/")
				memberAvatar = td[0].select("a")
					.select("img")
					.attr("src")
				url = td[2].select(".item_title")
					.select("a")
					.attr("href")
				id = PATTERN_NUMBERS.find(url!!)!!.value.toInt()
				title = td[2].select(".item_title")
					.select("a")
					.text()
				nodeName = td[2].selectFirst(".small.fade")
					.select(".node")
					.text()
				replies = if (td[3].selectFirst(".count_livid") == null) {
					0
				} else {
					td[3].selectFirst(".count_livid")
						.text()
						.toInt()
				}

				var strList: List<String> = td[2].select(".small.fade")[1]
					.text()
					.split(" • ")
				last_modified_str = strList[0]
			}

			topicList.add(topicListBean)
		}
		return topicList
	}
}