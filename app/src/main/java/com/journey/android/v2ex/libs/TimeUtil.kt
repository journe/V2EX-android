package com.journey.android.v2ex.libs

import android.text.format.DateUtils

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
            DateUtils.getRelativeTimeSpanString(created, now, DateUtils.MINUTE_IN_MILLIS,
                    DateUtils.FORMAT_ABBREV_RELATIVE)
        return text.toString()
    }
}