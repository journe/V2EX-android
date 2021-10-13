package com.journey.android.v2ex.utils

import android.widget.Toast
import com.journey.android.v2ex.libs.SpKey
import com.journey.android.v2ex.libs.SpUtils

object UserState {
	var username: String? = null
		private set
	private var mHasUnread: Boolean = false
	private var mHasAward: Boolean = false

	val islogin = SpUtils.getBoolean(SpKey.IS_LOGIN, false)

	fun init() {
//        RxBus.post(NewUnreadEvent(0))

		username = SpUtils.getString(SpKey.KEY_USERNAME, "")

//		RxBus.subscribe<DailyAwardEvent> {
//			mHasAward = it.mHasAward
//		}
	}

	fun handleInfo(info: MyselfParser.MySelfInfo?, pageType: PageType) {
		if (info == null) {
			logout()
			return
		}

		if (pageType === PageType.Topic) {
			return
		}

		if (info.unread > 0) {
			mHasUnread = true
			RxBus.post(NewUnreadEvent(info.unread))
		}
		if (pageType === PageType.Tab && info.hasAward != mHasAward) {
			RxBus.post(DailyAwardEvent(info.hasAward))
		}
	}

	fun login(username: String, avatar: Avatar) {
		ConfigDao.put(ConfigDao.KEY_AVATAR, avatar.baseUrl)
		ConfigDao.put(ConfigDao.KEY_USERNAME, username)

		UserState.username = username
		TrackerUtils.setUserId(username)

		AppCtx.eventBus.post(LoginEvent(username))
		ExecutorUtils.execute { UserUtils.checkDailyAward() }
	}

	fun logout() {
		username = null
		RequestHelper.cleanCookies()

		ConfigDao.remove(ConfigDao.KEY_USERNAME)
		ConfigDao.remove(ConfigDao.KEY_AVATAR)

		TrackerUtils.setUserId(null)
		CrashlyticsUtils.setUserState(false)

		ExecutorUtils.runInUiThread {
			Toast.makeText(
				AppCtx.instance, R.string.toast_has_sign_out,
				Toast.LENGTH_LONG
			).show()
		}
		AppCtx.eventBus.post(LoginEvent())
	}

	fun isLoggedIn(): Boolean {
		return username != null
	}

	fun hasUnread(): Boolean {
		return mHasUnread
	}

	fun clearUnread() {
		mHasUnread = false
		RxBus.post(NewUnreadEvent(0))
	}

	fun hasAward(): Boolean {
		return mHasAward
	}
}
