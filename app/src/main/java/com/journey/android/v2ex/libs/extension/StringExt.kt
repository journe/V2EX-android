package com.journey.android.v2ex.libs.extension

import okhttp3.ResponseBody
import org.jsoup.Jsoup
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by journey on 2021/10/12.
 */
fun Date.string(format: String = "yyyy-MM-dd HH:mm"): String {
    return SimpleDateFormat(format, Locale.getDefault()).format(this)
}

fun String.toJsoup() = Jsoup.parse(this)

fun ResponseBody.toJsoup() = Jsoup.parse(this.string())