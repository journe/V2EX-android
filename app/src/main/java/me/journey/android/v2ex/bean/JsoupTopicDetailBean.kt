package me.journey.android.v2ex.bean

import java.util.regex.Pattern

class JsoupTopicDetailBean {
    private val PATTERN = Pattern.compile("/t/(\\d+?)(?:\\W|$)")
    private val mId: Int = 0
    private val mTitle: String = ""
    private val mContent: String = ""
    private val mReplies: Int = 0
    private val mReplyTime: String = ""
    private val mHasInfo: Boolean = false
    private val mFavored: Boolean = false
    @Transient private var mHasRead: Boolean = false
}