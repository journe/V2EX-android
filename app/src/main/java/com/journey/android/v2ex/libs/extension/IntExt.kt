package com.journey.android.v2ex.utils.extension

import java.text.DecimalFormat

/**
 * Created by journey on 2020/5/27.
 */
fun Int.countStr(): String {
    return if (this > 9999) {
        val df = DecimalFormat("0.0")
        "${df.format(this.toDouble() / 10000)}万"
    } else {
        this.toString()
    }
}

