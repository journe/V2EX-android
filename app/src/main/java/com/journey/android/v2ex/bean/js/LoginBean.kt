package com.journey.android.v2ex.bean.js

import com.journey.android.v2ex.utils.Constants

class LoginBean {
    var account: String = ""
    var password: String = ""
    var next: String = ""
    var once: Int = 0
    var captcha: String = ""

    // /_captcha?once=57631
    fun genCaptcha(): String {
        return Constants.BASE_URL + "/_captcha?once=$once"
    }
}