package com.journey.android.v2ex.net.parser

import com.journey.android.v2ex.libs.TimeUtil
import com.journey.android.v2ex.model.api.TopicsListItemBean
import com.journey.android.v2ex.model.jsoup.MemBerReplyItemBean
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element

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
object MemberParser {
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
		val topicList: MutableList<TopicsListItemBean> = mutableListOf()
		val boxes = doc.body()
			.selectFirst("#Wrapper")
			.selectFirst(".content")
			.select(".box")
		if (boxes.count() == 3) {
			genTopicList(boxes[1], topicList)
		} else {
			genTopicList(boxes[0], topicList)
		}
		return topicList
	}

	private fun genTopicList(
		box: Element,
		topicList: MutableList<TopicsListItemBean>
	) {
		val content = box
			.select(".cell.item")
			.select("table")
			.select("tbody")
			.select("tr")

		for (element in content) {
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
	}

	@JvmStatic
	fun parseReply(doc: Document): List<MemBerReplyItemBean> {
		val list = mutableListOf<MemBerReplyItemBean>()

		val elements =
			doc.getElementsByAttributeValue("id", "Main")?.first()?.getElementsByClass("box")
				?.first()
		elements?.let {
			for (e in elements.getElementsByClass("dock_area")) {
				val model = MemBerReplyItemBean()
				val titleElement = e.getElementsByAttributeValueContaining("href", "/t/").first()
				val title = titleElement.text()
				val fakeId = titleElement.attr("href").removePrefix("/t/")
				val create = e.getElementsByClass("fade").first().ownText()
				model.topicTitle = title
				model.topicId = fakeId.split("#")[0].toInt()
				model.create = TimeUtil.toUtcTime(create)
				val contentElement: Element? = e.nextElementSibling()
				val content = contentElement?.getElementsByClass("reply_content")?.first()
				model.content = content?.html() ?: ""
				list.add(model)
			}
		}
		return list
	}

}