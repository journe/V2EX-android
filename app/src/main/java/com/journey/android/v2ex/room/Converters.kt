package com.journey.android.v2ex.room

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.journey.android.v2ex.model.api.MemberBean
import com.journey.android.v2ex.model.api.NodeBean
import com.journey.android.v2ex.model.api.TopicShowSubtle
import okhttp3.Cookie
import java.util.*

/**
 * Created by journey on 2020/5/20.
 */
class Converters {
  @TypeConverter
  fun fromTimestamp(value: Long?): Date? {
    return value?.let { Date(it) }
  }

  @TypeConverter
  fun dateToTimestamp(date: Date?): Long? {
    return date?.time?.toLong()
  }

  @TypeConverter
  fun stringToPoll(value: String?): Any? {
    return Gson().fromJson(value, Any::class.java)
  }

  @TypeConverter
  fun pollToString(data: Any?): String? {
    return Gson().toJson(data)
  }

  @TypeConverter
  fun listStringToString(data: List<String>?): String? {
    return Gson().toJson(data)
  }

  @TypeConverter
  fun stringToListString(value: String?): List<String>? {
    return Gson().fromJson(value, object : TypeToken<List<String>>() {}.type)
  }

  @TypeConverter
  fun stringToCookie(value: String?): List<Cookie>? {
    return Gson().fromJson(value, object : TypeToken<List<Cookie>>() {}.type)
  }

  @TypeConverter
  fun CookieToString(data: List<Cookie>?): String? {
    return Gson().toJson(data)
  }

  @TypeConverter
  fun stringToTopicShowSubtle(value: String?): List<TopicShowSubtle>? {
    return Gson().fromJson(value, object : TypeToken<List<TopicShowSubtle>>() {}.type)
  }

  @TypeConverter
  fun TopicShowSubtleToString(data: List<TopicShowSubtle>?): String? {
    return Gson().toJson(data)
  }

  @TypeConverter
  fun stringToMemberBean(value: String?): MemberBean? {
    return Gson().fromJson(value, MemberBean::class.java)
  }

  @TypeConverter
  fun MemberBeanToString(data: MemberBean?): String? {
    return Gson().toJson(data)
  }

  @TypeConverter
  fun stringToNodeBean(value: String?): NodeBean? {
    return Gson().fromJson(value, NodeBean::class.java)
  }

  @TypeConverter
  fun NodeBeanToString(data: NodeBean?): String? {
    return Gson().toJson(data)
  }
}