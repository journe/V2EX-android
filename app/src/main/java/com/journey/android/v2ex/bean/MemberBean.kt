package com.journey.android.v2ex.bean

class MemberBean {
    constructor(username: String, avatar: String?) {
        this.username = username
        this.avatar = avatar
    }
    var username: String? = ""
    var avatar: String? = ""
}