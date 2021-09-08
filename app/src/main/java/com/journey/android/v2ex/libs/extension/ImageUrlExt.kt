package com.journey.android.v2ex.libs.extension

fun String.color(): String =
  "$this?x-oss-process=image/average-hue"

fun String?.largeAvatar(): String? = this?.replace("_mini", "_large")

fun String.thumbnail(): String =
  "$this?x-oss-process=image/auto-orient,1/interlace,1/quality,q_75/format,jpg"

fun String.info(): String =
  "$this?x-oss-process=image/info"

fun String.imageResize11(): String =
  "$this?x-oss-process=image/resize,m_fill,w_400,h_400/quality,q_80/format,jpg"

fun String.imageResize34(): String =
  "$this?x-oss-process=image/resize,m_fill,w_300,h_400/quality,q_80/format,jpg"

fun String.imageResize43(): String =
  "$this?x-oss-process=image/resize,m_fill,w_400,h_300/quality,q_80/format,jpg"

//视频预览图
fun String.snapshot(): String =
  "$this?x-oss-process=video/snapshot,t_0,f_jpg"

//展示大图
fun String.lfitW1080Jpg(): String =
  "$this?x-oss-process=image/auto-orient,1/resize,m_fill,w_1080/quality,q_80/format,jpg"

//横幅图片=======================
//抽盒机列表图-分享图
fun String.fillW500H400Jpg(): String =
  "$this?x-oss-process=image/auto-orient,1/resize,m_fill,w_500,h_400/quality,q_80/format,jpg"

//三等分信息流的横幅图
fun String.fillW320H240Jpg(): String =
  "$this?x-oss-process=image/auto-orient,1/resize,m_fill,w_320,h_240/quality,q_80/format,jpg"