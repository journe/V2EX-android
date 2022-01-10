package com.journey.android.v2ex.utils

import android.app.backup.BackupManager
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.journey.android.v2ex.model.Tab

class PrefStore internal constructor(context: Context) : SharedPreferences.OnSharedPreferenceChangeListener {

  private val mPreferences: SharedPreferences =
    PreferenceManager.getDefaultSharedPreferences(context)

  /**
   * @see Tab.getTabsToShow
   */
  val tabsToShow: List<Tab>
    get() {
      val string = mPreferences.getString(PREF_TABS_TO_SHOW, null)
      return Tab.getTabsToShow(string)
    }

  val isAlwaysShowReplyForm: Boolean
    get() = mPreferences.getBoolean(PREF_ALWAYS_SHOW_REPLY_FORM, false)

  val isUndoEnabled: Boolean
    get() = mPreferences.getBoolean(PREF_ENABLE_UNDO, false)

  val isContentSelectable: Boolean
    get() = mPreferences.getBoolean(PREF_CONTENT_SELECTABLE, false)

  var userName: String
    get() = mPreferences.getString(NAME, "") ?: ""
    set(value) {
      mPreferences.edit()
          .putString(NAME, value)
          .apply()
    }
  var userPass: String
    get() = mPreferences.getString(PASS, "") ?: ""
    set(value) {
      mPreferences.edit()
          .putString(PASS, value)
          .apply()
    }

  var personalAccessToken: String
    get() = mPreferences.getString(PERSONAL_ACCESS_TOKEN, "") ?: ""
    set(value) {
      mPreferences.edit()
          .putString(PERSONAL_ACCESS_TOKEN, value)
          .apply()
    }

  init {
    initPref()
    mPreferences.registerOnSharedPreferenceChangeListener(this)
  }

  private fun initPref() {
    val version = mPreferences.getInt(PREF_LAST_PREF_VERSION, 0)

    if (version == VERSION) {
      return
    }
  }

  fun alwaysLoadImage(): Boolean {
    return mPreferences.getBoolean(PREF_ALWAYS_LOAD_IMAGE, false)
  }

  fun shouldLoadImage(): Boolean {
    return alwaysLoadImage()
  }

  fun shouldReceiveNotifications(): Boolean {
    //if (!UserState.INSTANCE.isLoggedIn()) {
    //    Log.i(TAG, "Guest can't receive notifications");
    //    return false;
    //}

    return mPreferences.getBoolean(PREF_RECEIVE_NOTIFICATIONS, false)
  }

  override fun onSharedPreferenceChanged(
    sharedPreferences: SharedPreferences,
    key: String
  ) {
    requestBackup()
  }

  fun unregisterPreferenceChangeListener(
    listener: SharedPreferences.OnSharedPreferenceChangeListener
  ) {
    mPreferences.unregisterOnSharedPreferenceChangeListener(listener)
  }

  fun registerPreferenceChangeListener(
    listener: SharedPreferences.OnSharedPreferenceChangeListener
  ) {
    mPreferences.registerOnSharedPreferenceChangeListener(listener)
  }

  companion object {
    private val VERSION = 3

    var instance: PrefStore = PrefStore(Utils.getContext())
    val PREF_ALWAYS_LOAD_IMAGE = "always_load_image"
    val PREF_TABS_TO_SHOW = "tabs_to_show"
    val PREF_RECEIVE_NOTIFICATIONS = "receive_notifications"
    val PREF_ALWAYS_SHOW_REPLY_FORM = "always_show_reply_form"
    val PREF_ENABLE_UNDO = "enable_undo"
    val PREF_LAST_PREF_VERSION = "last_app_version"
    val PREF_CONTENT_SELECTABLE = "content_selectable"
    val NAME = "username"
    val PASS = "password"
    val PERSONAL_ACCESS_TOKEN = "personal_access_token"

    fun requestBackup() {
      val manager = BackupManager(Utils.getContext())
      manager.dataChanged()
    }
  }
}
