package me.journey.android.v2ex.bean

import java.util.regex.Pattern

class JsoupTopicDetailBean {
    private val PATTERN = Pattern.compile("/t/(\\d+?)(?:\\W|$)")
    private val PATTERN_TOPIC_REPLY_TIME = "at (.+?),".toRegex()
    private val PATTERN_POSTSCRIPT = "·\\s+(.+)".toRegex()
    private val PATTERN_NUMBERS = "\\d+".toRegex()

    var mId: Int = 0
    var mTitle: String = ""
    var mContent: String = ""
    var mReplies: Int = 0
    var mReplyTime: String = ""
}