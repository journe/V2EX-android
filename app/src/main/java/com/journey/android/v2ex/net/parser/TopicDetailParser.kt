package com.journey.android.v2ex.net.parser

import com.journey.android.v2ex.model.api.RepliesShowBean
import com.journey.android.v2ex.model.api.TopicShowSubtle
import com.journey.android.v2ex.model.api.TopicsShowBean
import com.journey.android.v2ex.utils.Constants
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
object TopicDetailParser {

    @JvmStatic
    fun parseTopicDetail(doc: Document): TopicsShowBean {
        val mainContent = doc.body()
            .selectFirst("#Wrapper")
            .selectFirst(".content")
            .select(".box")
        val topic = mainContent[0]
        val topicDetailBean = TopicsShowBean()
//    Logger.d(topic.toString())
        if (topic.selectFirst(".header") == null) {
            return topicDetailBean
        } else {
            val title = topic.selectFirst(".header")
                ?.selectFirst("h1")
            if (title == null) {
                topicDetailBean.title = "需要登录"
                return topicDetailBean
            } else {
                topicDetailBean.title = title.text()
            }
        }
        if (topic.selectFirst(".cell") != null
            && topic.selectFirst(".cell")
                .selectFirst(".topic_content") != null
        ) {
            topicDetailBean.content = topic.selectFirst(".cell")
                .selectFirst(".topic_content")
                .html()
        }

        topicDetailBean.member.username = topic.selectFirst(".header")
            .selectFirst("small.gray")
            .selectFirst("a")
            .text()
        topicDetailBean.member.avatar_large = topic.selectFirst(".header")
            .selectFirst(".fr")
            .select("img")
            .attr("src")

        topicDetailBean.node.name = topic.selectFirst(".header")
            .select("a")[2]
            .text()
        topicDetailBean.node.url = topic.selectFirst(".header")
            .select("a")[2]
            .attr("href")
        topicDetailBean.created_str = topic.selectFirst(".header")
            .selectFirst("small.gray")
            .text()

        val replyString = doc.body()
            .selectFirst("#Wrapper")
            .selectFirst(".content")
            .select(".box")[1]
            .selectFirst(".cell")
            .selectFirst(".gray")
            .text().let {
                it.substring(0, it.indexOf("条回复")).trim()
            }
        topicDetailBean.replies = if (replyString.isNullOrEmpty()) 0 else replyString.toInt()

        topic.select(".subtle")
            ?.let {
                topicDetailBean.subtles = mutableListOf()
                for (element in it) {
                    val subtle = TopicShowSubtle()
                    subtle.title = element.selectFirst(".fade")
                        .text()
                    subtle.content = element.selectFirst(".topic_content")
                        .html()
//            Logger.d(subtle.title + subtle.content)
                    topicDetailBean.subtles!!.add(subtle)
                }
            }

        return topicDetailBean
    }

    fun parseComments(doc: Document): List<RepliesShowBean> {
        val comments = doc.body()
            .selectFirst("#Wrapper")
            .selectFirst(".content")
            .select(".box")[1]
            .select("div[id^=r_]")
        val commentBeanList = mutableListOf<RepliesShowBean>()
        for (item in comments) {
            if (item.selectFirst("table") == null
                || item.attr("id")
                    .isNullOrEmpty()
            ) {
                continue
            }

            val comment = item.selectFirst("table")
                .selectFirst("tbody")
                .selectFirst("tr")
            val commentBean = RepliesShowBean()
            commentBean.id = item.id()
                .replace("r_", "")
                .toInt()
            commentBean.content = comment
                .selectFirst("td[align = left]")
                .selectFirst("div.reply_content")
                .text()
            commentBean.content_rendered = comment
                .selectFirst("td[align = left]")
                .selectFirst("div.reply_content")
                .html()
            commentBean.floor = comment
                .selectFirst("td[align = left]")
                .selectFirst("div.fr")
                .selectFirst("span.no")
                .text()
                .toInt()
            comment
                .selectFirst("td[align = left]")
                .selectFirst("span[class = fade small]")
                ?.let {
                    commentBean.created_str = it.text()
                }
            comment
                .selectFirst("td[align = left]")
                .selectFirst("span[class = small fade]")
                ?.let {
                    commentBean.heart = it.text()
                        .toInt()
                }

            commentBean.member.username = comment
                .selectFirst("td[align = left]")
                .selectFirst("strong")
                .selectFirst("a")
                .text()
            commentBean.member.avatar_large = comment
                .selectFirst("td")
                .selectFirst("img")
                .attr("src")

            commentBeanList.add(commentBean)
        }
        return commentBeanList
    }

}