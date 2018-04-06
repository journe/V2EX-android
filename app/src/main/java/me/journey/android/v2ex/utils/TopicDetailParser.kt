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

    /**
     * <div class="box" style="border-bottom: 0px;">
     *  <div class="header">
     *      <div class="fr">
     *      <a href="/member/journey"><img src="//cdn.v2ex.com/avatar/d3dd/1223/95319_large.png?m=1427878830" class="avatar" border="0" align="default"></a>
     *      </div>
     *      <a href="/">V2EX</a>
     *      <span class="chevron">&nbsp;›&nbsp;</span>
     *      <a href="/go/chrome">Chrome</a>
     *      <div class="sep10"></div>
     *      <h1>更新 Chrome47 之后，工具栏竟然不对齐了</h1>
     *      <div id="topic_241746_votes" class="votes">
     *      <a href="javascript:" onclick="upVoteTopic(241746);" class="vote"><li class="fa fa-chevron-up"></li></a> &nbsp;
     *      <a href="javascript:" onclick="downVoteTopic(241746);" class="vote"><li class="fa fa-chevron-down"></li></a>
     *      </div> &nbsp;
     *      <small class="gray"><a href="/member/journey">journey</a> · 2015-12-07 15:47:14 +08:00 · 1400 次点击</small>
     *      </div>
     *      <div class="outdated">
     *      这是一个创建于 837 天前的主题，其中的信息可能已经有所发展或是发生改变。
     *      </div>
     *      <div class="cell">
     *          <div class="topic_content">
     *          <h1>如图：</h1> <p><img src="http://ww2.sinaimg.cn/large/81219c67gw1eyr4pm8xzkj209r03ujrh.jpg" alt=""></p>
     *          <p><img src="http://ww2.sinaimg.cn/large/81219c67gw1eyr54bsvruj209d03l0t0.jpg" alt=""></p>
     *          <p><img src="http://ww2.sinaimg.cn/large/81219c67gw1eyr54qwpcrj208902i0ss.jpg" alt=""></p>
     *          <h2>左边的按钮样式也变得不正常：</h2>
     *          <p><img src="http://ww2.sinaimg.cn/large/81219c67gw1eyr52uhgaaj207t037dg2.jpg" alt=""><br> 版本 47.0.2526.73 m</p>
     *          <p><strong>强迫症表示无法忍受！！！</strong></p>
     *          </div>
     *      </div>
     *      <div class="subtle">
     *      <span class="fade">第 1 条附言 &nbsp;·&nbsp; 2015-12-07 17:11:17 +08:00</span>
     *      <div class="sep5"></div>
     *      <div class="topic_content">
     *          <div class="markdown_body">
     *          <p>解决办法出现在 11 楼</p>
     *          <blockquote>
     *          <p>chrome://flags/#top-chrome-md <br> 调整为非 Material 即可， 48 版本以后其他 2 个选项就对齐了</p>
     *          </blockquote>
     *          </div>
     *      </div>
     *  </div>
     * </div>
     */
    @JvmStatic
    fun parseTopicDetail(doc: Document): JsoupTopicDetailBean {
        val mainContent = doc.body().selectFirst("#Wrapper")
                .selectFirst(".content")
                .selectFirst("#Main")
                .select(".box")
        val topic = mainContent[0]
        var topicDetailBean = JsoupTopicDetailBean()
        if (topic.selectFirst(".header") == null) {
            return topicDetailBean
        } else {
            topicDetailBean.title = topic.selectFirst(".header").selectFirst("h1").text()
        }
        if (topic.selectFirst(".cell").selectFirst(".topic_content") != null) {
            topicDetailBean.content = topic.selectFirst(".cell").selectFirst(".topic_content").html()
        }

        val username = topic.selectFirst(".header")
                .selectFirst("small.gray")
                .selectFirst("a")
                .text()
        val avatar = topic.selectFirst(".header")
                .selectFirst(".fr")
                .select("img")
                .attr("src")
        topicDetailBean.memberBean = MemberBean(username, avatar)

        if (mainContent.size > 1) {
            topicDetailBean.comments = parseComments(mainContent[1])
        }
        return topicDetailBean
    }

    /**
     * <div id="r_2700391" class="cell">
     *   <table cellpadding="0" cellspacing="0" border="0" width="100%">
     *      <tbody>
     *          <tr>
     *              <td width="48" valign="top" align="center">
     *                  <img src="//cdn.v2ex.com/avatar/f38c/5601/70218_normal.png?m=1412573936" class="avatar" border="0" align="default">
     *              </td>
     *              <td width="10" valign="top"></td>
     *              <td width="auto" valign="top" align="left">
     *                  <div class="fr">
     *                  &nbsp; &nbsp;
     *                  <span class="no">1</span>
     *                  </div>
     *                  <div class="sep3"></div> <strong><a href="/member/jy02201949" class="dark">jy02201949</a></strong>&nbsp; &nbsp;<span class="ago">2015-12-07 15:52:20 +08:00</span>
     *                  <div class="sep5"></div>
     *                  <div class="reply_content">
     *                      <img src="http://ww2.sinaimg.cn/large/44d57f0dgw1eyr5aw35jtj21f1012q3i.jpg" class="embedded_image"> 没有问题
     *                  </div>
     *              </td>
     *          </tr>
     *      </tbody>
     *   </table>
     * </div>
     */
    private fun parseComments(element: Element): ArrayList<CommentBean> {
        val comments = element.select(".cell")
        var commentBeanList = ArrayList<CommentBean>()
        for (item in comments) {
            if (item.selectFirst("table") == null) {
                continue
            }
            val comment = item.selectFirst("table")
                    .selectFirst("tbody")
                    .selectFirst("tr")
            var commentBean = CommentBean()
            commentBean.id = comment.attr("id")
            commentBean.content = comment
                    .selectFirst("td[align = left]")
                    .selectFirst(".reply_content")
                    .html()
            commentBean.floor = comment
                    .selectFirst("td[align = left]")
                    .selectFirst("span.no")
                    .text()
                    .toInt()
            commentBean.replyTime = comment
                    .selectFirst("td[align = left]")
                    .selectFirst("span.ago")
                    .text()

            val username = comment
                    .selectFirst("td[align = left]")
                    .selectFirst("strong")
                    .selectFirst("a")
                    .text()
            val avatar = comment
                    .selectFirst("td[align = center]")
                    .selectFirst("img")
                    .attr("src")
            commentBean.member = MemberBean(username, avatar)

            commentBeanList.add(commentBean)
        }
        return commentBeanList
    }


}