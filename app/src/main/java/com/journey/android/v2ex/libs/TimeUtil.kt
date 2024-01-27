package com.journey.android.v2ex.libs

import android.text.format.DateUtils
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by journey on 2018/8/9.
 */
object TimeUtil {
    fun calculateTime(ts: Long): String {
        val created = ts * 1000
        val now = System.currentTimeMillis()
        val difference = now - created
        val text = if (difference >= 0 && difference <= DateUtils.MINUTE_IN_MILLIS)
            "刚刚"
        else
            DateUtils.getRelativeTimeSpanString(
                created, now, DateUtils.MINUTE_IN_MILLIS,
                DateUtils.FORMAT_ABBREV_RELATIVE
            )
        return text.toString()
    }

    /**
     * 遗憾的是只能通过这样得到一个不准确的时间。
     * 算出绝对时间是为了保存如缓存，不然可以直接用得到的时间展示。
     *
     * @param timeStr 两个点中间的字符串，包括空格
     * *
     * @return long value
     */
    fun toUtcTime(timeStr: String): Long {
        var theTime = timeStr

        theTime = theTime.trim()
        //       String theTime = time.replace("&nbsp", "");
        //        44 分钟前用 iPhone 发布
        //         · 1 小时 34 分钟前 · 775 次点击
        //         · 100 天前 · 775 次点击
        //       1992.02.03 12:22:22 +0800
        //       2017-09-26 22:27:57 PM
        //      刚刚
        //其中可能出现一些奇怪的字符，你可能以为是空格。
        var created = System.currentTimeMillis() / 1000 // ms -> second

        val second = theTime.indexOf("秒")
        val hour = theTime.indexOf("小时")
        val minute = theTime.indexOf("分钟")
        val day = theTime.indexOf("天")
        val now = theTime.indexOf("刚刚")

        try {
            when {
//                theTime.isEmpty() -> return System.currentTimeMillis()/1000
                second != -1 -> return created
                hour != -1 -> created -= theTime.substring(0, hour).toLong() * 60 * 60 +
                        theTime.substring(hour + 2, minute).toLong() * 60
                day != -1 -> created -= theTime.substring(0, day).toLong() * 60 * 60 * 24
                minute != -1 -> created -= theTime.substring(0, minute).toLong() * 60
                now != -1 -> return created
                else -> {
                    val sdf = SimpleDateFormat("yyyy-MM-dd hh:mm:ss +08:00", Locale.getDefault())
                    val date = sdf.parse(theTime.trim())
                    created = date?.time?.div(1000) ?: 0
                }
            }
        } catch (e1: NumberFormatException) {
//            XLog.tag("TimeUtil").e("NumberFormatException error: $theTime, $timeStr")
        } catch (e2: StringIndexOutOfBoundsException) {
//            XLog.tag("TimeUtil").e(" StringIndexOutOfBoundsException error: $theTime, $timeStr")
        } catch (e2: ParseException) {
            try {
                val ccc = SimpleDateFormat("yyyy-MM-dd hh:mm:ss a", Locale.US)
                val date = ccc.parse(theTime.trim())
                created = date?.time?.div(1000) ?: 0
            } catch (ignore: ParseException) {
                try {
                    val ccc = SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss", Locale.US)
                    val date = ccc.parse(theTime.trim())
                    created = date?.time?.div(1000) ?: 0
                } catch (ignre: ParseException){
//                    XLog.tag("TimeUtil").e("time str parse error: $theTime")
                }
            }
        }

        return created
    }
}