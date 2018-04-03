package me.journey.android.v2ex.utils

import me.journey.android.v2ex.bean.CommentBean
import me.journey.android.v2ex.bean.JsoupTopicDetailBean
import me.journey.android.v2ex.bean.MemberBean
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
object TopicDetailParser {
    private val PATTERN_TOPIC_REPLY_TIME = "at (.+?),".toRegex()
    private val PATTERN_POSTSCRIPT = "·\\s+(.+)".toRegex()
    private val PATTERN_NUMBERS = "\\d+".toRegex()

    @JvmStatic
    fun parseTopicDetail(doc: Document): JsoupTopicDetailBean {
        val mainContent = doc.body().selectFirst("#Wrapper")
                .selectFirst(".content")
                .selectFirst("#Main")
                .select(".box")
        val topic = mainContent[0]
        if (mainContent.size > 1) {
            parseComments(mainContent[1])
        }

        var bean = JsoupTopicDetailBean()
        if (topic.selectFirst(".header") == null) {
            return bean
        } else {
            bean.title = topic.selectFirst(".header").selectFirst("h1").text()
        }
        if (topic.selectFirst(".cell").selectFirst(".topic_content") != null) {
            bean.content = topic.selectFirst(".cell").selectFirst(".topic_content").html()
        }

        val username = topic.selectFirst(".header").selectFirst(".fr").select("a").attr("href").substringAfterLast("/")
        val avatar = topic.selectFirst(".header").selectFirst(".fr").select("img").attr("src")
        bean.memberBean = MemberBean(username, avatar)
//        Logger.d(bean.title)
//        Logger.d(bean.content)
        return bean
    }

    private fun parseComments(element: Element) {
        val comments = element.select(".cell")
        for (comment in comments) {
            if (comment.selectFirst("table") == null) {
                continue
            }
            var commentBean = CommentBean()
            commentBean.id = comment.attr("id")
            commentBean.content = comment.selectFirst("table")
                    .selectFirst("tbody")
                    .selectFirst("tr")
                    .selectFirst("td[align = left]")
                    .selectFirst(".reply_content")
                    .html()
        }
    }


}