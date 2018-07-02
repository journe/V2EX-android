package com.journey.android.v2ex.bean

class TopicCommentBean {
    var id: String = ""
    var content: String = ""
    var member: MemberBean = MemberBean("", "")
    var replyTime: String = ""
    var thanks: Int = 0
    var floor: Int = 0
    var thanked: Boolean = false
}