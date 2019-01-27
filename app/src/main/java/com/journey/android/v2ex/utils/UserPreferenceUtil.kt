package com.journey.android.v2ex.utils

import android.content.Context
import android.content.SharedPreferences
import com.journey.android.v2ex.V2exApplication

object UserPreferenceUtil {
  val NAME = "username"
  val PASS = "password"
  val sp: SharedPreferences =
    V2exApplication.instance.getSharedPreferences("user", Context.MODE_PRIVATE)

  operator fun contains(key: String): Boolean {
    return sp!!.contains(key)
  }

  fun getValue(
    key: String,
    defValue: String
  ): String? {
    return sp.getString(key, defValue)
  }

  fun setValue(
    key: String,
    value: String
  ) {
    val editor = sp!!.edit()
    editor.putString(key, value)
    editor.apply()
  }

  fun getValue(
    key: String,
    defValue: Int
  ): Int {
    return sp!!.getInt(key, defValue)
  }

  fun setValue(
    key: String,
    value: Int
  ) {
    val editor = sp!!.edit()
    editor.putInt(key, value)
    editor.apply()
  }

  fun getValue(
    key: String,
    defValue: Boolean
  ): Boolean {
    return sp!!.getBoolean(key, defValue)
  }

  fun setValue(
    key: String,
    value: Boolean
  ) {
    val editor = sp!!.edit()
    editor.putBoolean(key, value)
    editor.apply()
  }

  fun clear() {
    sp!!.edit()
        .clear()
        .apply()
  }
}
