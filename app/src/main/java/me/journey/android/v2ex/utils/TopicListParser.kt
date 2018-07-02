package me.journey.android.v2ex.utils

import com.orhanobut.logger.Logger
import me.journey.android.v2ex.bean.TopicListBean
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
     *  <tr>
     *   <td width="48" valign="top" align="center">
     *       <a href="/member/Symo">
     *           <img src="//cdn.v2ex.com/avatar/192b/955e/148129_normal.png?m=1495178276" class="avatar" border="0" align="default">
     *               </a>
     *               </td>
     *   <td width="10"></td>
     *   <td width="auto" valign="middle">
     *       <span class="item_title">
     *          <a href="/t/466084#reply4">港行 mbp 送去更换键盘的话, 只能去直营店吗?</a>
     *       </span>
     *       <div class="sep5"></div>
     *       <span class="topic_info">
     *          <div class="votes"></div>
     *          <a class="node" href="/go/mbp">MacBook Pro</a> &nbsp;•&nbsp;
     *          <strong><a href="/member/Symo">Symo</a></strong> &nbsp;•&nbsp; 2 天前 &nbsp;•&nbsp; 最后回复来自
     *          <strong><a href="/member/wangfei324017">wangfei324017</a></strong>
     *       </span>
     *   </td>
     *   <td width="70" align="right" valign="middle">
     *       <a href="/t/466084#reply4" class="count_livid">4</a>
     *   </td>
     *  </tr>
     */

    @JvmStatic
    fun parseTopicList(doc: Document): ArrayList<TopicListBean> {
        val content = doc.body().selectFirst("#Wrapper")
                .selectFirst(".content")
                .selectFirst("#Main")
                .select(".box")
                .select(".item")
                .select("table")
                .select("tbody")
                .select("tr")
        val topicList: ArrayList<TopicListBean> = ArrayList()
        for (element in content) {
            Logger.d(element.toString())
            val td = element.select("td")
            val topicListBean = TopicListBean()
            topicListBean.member!!.username = td[0].select("a").attr("href")
                    .substringAfterLast("/")
            topicListBean.member!!.avatar_large = td[0].select("a").select("img")
                    .attr("src")
            topicListBean.url = td[2].select(".item_title").select("a").attr("href")
            topicListBean.id = PATTERN_NUMBERS.find(topicListBean.url!!)!!.value.toInt()
            topicListBean.title = td[2].select(".item_title").select("a").text()
            topicListBean.node!!.title = td[2].select(".topic_info").select(".node").text()
            val replies = td[3].select("a").text()
            if (replies.isEmpty()) {
                topicListBean.replies = 0
            } else {
                topicListBean.replies = td[3].select("a").text().toInt()
            }

            var strList: List<String> = td[2].select(".topic_info").text().split(" • ")
            if (strList.size > 2) {
                topicListBean.last_modified_str = strList[2]
            } else {
                topicListBean.last_modified_str = ""
            }
            topicList.add(topicListBean)
        }
        return topicList
    }
}