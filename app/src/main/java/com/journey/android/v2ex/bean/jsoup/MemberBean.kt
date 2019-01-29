package com.journey.android.v2ex.bean.jsoup

class MemberBean {
    constructor(username: String, avatar: String?) {
        this.username = username
        this.avatar = avatar
    }
    var username: String? = ""
    var avatar: String? = ""
}