package me.journey.android.v2ex.bean

import java.util.regex.Pattern

class JsoupTopicDetailBean {
    private val PATTERN = Pattern.compile("/t/(\\d+?)(?:\\W|$)")
    private val PATTERN_TOPIC_REPLY_TIME = "at (.+?),".toRegex()
    private val PATTERN_POSTSCRIPT = "·\\s+(.+)".toRegex()
    private val PATTERN_NUMBERS = "\\d+".toRegex()

    var id: Int = 0
    var title: String = ""
    var content: String = ""
    var replies: Int = 0
    var replyTime: String = ""
    var memberBean: MemberBean = MemberBean("", "")
    var comments: ArrayList<CommentBean>? = null
}