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

//市场双列信息流
fun String.fillW480H360Jpg(): String =
  "$this?x-oss-process=image/auto-orient,1/resize,m_fill,w_480,h_360/quality,q_80/format,jpg"

//横幅图片 图文型、Topic
fun String.fillW720H540Jpg(): String =
  "$this?x-oss-process=image/auto-orient,1/resize,m_fill,w_720,h_540/quality,q_80/format,jpg"

//抽选 Topic
fun String.fillW720H405Jpg(): String =
  "$this?x-oss-process=image/auto-orient,1/resize,m_fill,w_720,h_405/quality,q_80/format,jpg"

//banner(抽盒机首页)、商家背景图
fun String.fillW900H300Jpg(): String =
  "$this?x-oss-process=image/auto-orient,1/resize,m_fill,w_900,h_300/quality,q_80/format,jpg"

//潮玩族市场页Banner
fun String.fillW700H100Jpg(): String =
  "$this?x-oss-process=image/auto-orient,1/resize,m_fill,w_700,h_100/quality,q_80/format,jpg"

//竖幅图片=======================
//社区信息流的竖幅图
fun String.fillW240H320Jpg(): String =
  "$this?x-oss-process=image/auto-orient,1/resize,m_fill,w_240,h_320/quality,q_80/format,jpg"

//spu 图
fun String.lfitW250H400Jpg(): String =
  "$this?x-oss-process=image/auto-orient,1/resize,m_lfit,w_250,h_400/quality,q_80/format,jpg"

//图文型、Topic
fun String.fillW720H960Jpg(): String =
  "$this?x-oss-process=image/auto-orient,1/resize,m_fill,w_720,h_960/quality,q_80/format,jpg"

//方幅图片========================
//超迷你头像
fun String.fillW50H50Jpg(): String =
  "$this?x-oss-process=image/auto-orient,1/resize,m_fill,w_50,h_50/quality,q_80/format,jpg"

//小头像
fun String.fillW100H100Jpg(): String =
  "$this?x-oss-process=image/auto-orient,1/resize,m_fill,w_100,h_100/quality,q_80/format,jpg"

//中头像
fun String.fillW300H300Jpg(): String =
  "$this?x-oss-process=image/auto-orient,1/resize,m_fill,w_300,h_300/quality,q_80/format,jpg"

//大头像
fun String.fillW500H500Jpg(): String =
  "$this?x-oss-process=image/auto-orient,1/resize,m_fill,w_500,h_500/quality,q_80/format,jpg"

//定宽图片========================
//双列瀑布流的图片
fun String.lfitW480Jpg(): String =
  "$this?x-oss-process=image/auto-orient,1/resize,m_fill,w_480/quality,q_80/format,jpg"

//商品详情图
fun String.lfitW750Jpg(): String =
  "$this?x-oss-process=image/auto-orient,1/resize,m_fill,w_750/quality,q_80/format,jpg"

//抽盒机盒柜图片
fun String.lfitW180Jpg(): String =
  "$this?x-oss-process=image/auto-orient,1/resize,m_fill,w_180/quality,q_80/format,jpg"

//抽盒机猜盒卡片图片
fun String.lfitW240Jpg(): String =
  "$this?x-oss-process=image/auto-orient,1/resize,m_fill,w_240/quality,q_80/format,jpg"

fun String.lfitW320Jpg(): String =
  "$this?x-oss-process=image/auto-orient,1/resize,m_fill,w_320/quality,q_80/format,jpg"

fun String.resizeWidthHeight(
    width: Int,
    height: Int = 0
): String {
  return if (height <= 0) {
    "$this?x-oss-process=image/auto-orient,1/resize,m_fill,w_${width}/quality,q_80/format,jpg"
  } else {
    "$this?x-oss-process=image/auto-orient,1/resize,m_fill,w_${width}, h_${height}/quality,q_80/format,jpg"
  }
}