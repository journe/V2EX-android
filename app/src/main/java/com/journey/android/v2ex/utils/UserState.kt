package com.journey.android.v2ex.utils

import android.widget.Toast
import com.journey.android.v2ex.R
import com.journey.android.v2ex.base.BaseApplication
import com.journey.android.v2ex.libs.SpKey
import com.journey.android.v2ex.libs.SpUtils
import com.journey.android.v2ex.model.Avatar
import com.journey.android.v2ex.net.RetrofitService

object UserState {
	var username: String = ""
		private set
	private var mHasUnread: Boolean = false
	private var mHasAward: Boolean = false

	val islogin = SpUtils.getBoolean(SpKey.IS_LOGIN, false)!!

	val token = SpUtils.getString(SpKey.TOKEN, "")

	fun init() {
//        RxBus.post(NewUnreadEvent(0))

		username = SpUtils.getString(SpKey.KEY_USERNAME, "")!!
		username = SpUtils.getString(SpKey.KEY_AVATAR, "")!!

//		RxBus.subscribe<DailyAwardEvent> {
//			mHasAward = it.mHasAward
//		}
	}

//	fun handleInfo(info: MyselfParser.MySelfInfo?, pageType: PageType) {
//		if (info == null) {
//			logout()
//			return
//		}
//
//		if (pageType === PageType.Topic) {
//			return
//		}
//
//		if (info.unread > 0) {
//			mHasUnread = true
//			RxBus.post(NewUnreadEvent(info.unread))
//		}
//		if (pageType === PageType.Tab && info.hasAward != mHasAward) {
//			RxBus.post(DailyAwardEvent(info.hasAward))
//		}
//	}

	fun login(username: String, avatar: Avatar) {
		this.username = username

		SpUtils.put(SpKey.KEY_AVATAR, avatar.baseUrl)
		SpUtils.put(SpKey.KEY_USERNAME, username)

//		AppCtx.eventBus.post(LoginEvent(username))
//		ExecutorUtils.execute { UserUtils.checkDailyAward() }
	}

	fun logout() {
		username = ""
		SpUtils.put(SpKey.IS_LOGIN, false)
		SpUtils.put(SpKey.KEY_AVATAR, "")
		SpUtils.put(SpKey.KEY_USERNAME, "")
		RetrofitService.cleanCookies()
		Toast.makeText(
			BaseApplication.context,
			BaseApplication.context.resources.getText(R.string.toast_has_sign_out),
			Toast.LENGTH_SHORT
		).show()

//		TrackerUtils.setUserId(null)
//		CrashlyticsUtils.setUserState(false)
//
//		ExecutorUtils.runInUiThread {
//			Toast.makeText(
//				AppCtx.instance, R.string.toast_has_sign_out,
//				Toast.LENGTH_LONG
//			).show()
//		}
//		AppCtx.eventBus.post(LoginEvent())
	}

	fun isLoggedIn(): Boolean {
		return username != null
	}

	fun hasUnread(): Boolean {
		return mHasUnread
	}

	fun clearUnread() {
		mHasUnread = false
//		RxBus.post(NewUnreadEvent(0))
	}

	fun hasAward(): Boolean {
		return mHasAward
	}
}
