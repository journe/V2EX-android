package com.journey.android.v2ex.module.profile

import com.journey.android.v2ex.base.BaseRepository
import com.journey.android.v2ex.model.api.MemberBean
import com.journey.android.v2ex.model.jsoup.MemBerReplyItemBean
import com.journey.android.v2ex.net.RetrofitService
import com.journey.android.v2ex.net.parser.MemberParser
import com.journey.android.v2ex.room.AppDatabase
import com.journey.android.v2ex.utils.Constants
import kotlinx.coroutines.flow.Flow
import org.jsoup.Jsoup
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileRepository @Inject constructor(
	private val db: AppDatabase,
	private val apiService: RetrofitService
) : BaseRepository() {


	fun requestMemberBean(name: String): Flow<MemberBean?> = db.userInfoDao().getUserByName(name)

	suspend fun refresh(name: String) {
		val bean = apiService.getMemberInfo(name)
		db.userInfoDao().insert(bean)
	}

	suspend fun requestMemberReply(name: String, page: Int = 1): List<MemBerReplyItemBean> {
		val result = apiService.requestSuspend(Constants.MEMBER + "$name/replies?p=$page")
		//		insertResultIntoDb(listItemBean)
		return MemberParser.parseReply(Jsoup.parse(result.string()))
	}


//	private fun getRepliesByWeb(page: Int) {
//
//		val url =
//			"${NetManager.HTTPS_V2EX_BASE}/member/${arguments?.getString(Keys.KEY_USERNAME)}/replies?p=$page"
//
//		vCall(url).enqueue(object : Callback {
//			override fun onFailure(call: Call, e: IOException) {
//				NetManager.dealError(activity, -1)
//				swipeRefreshLayout.isRefreshing = false
//				mScrollListener?.loading = false
//			}
//
//			@Throws(IOException::class)
//			override fun onResponse(call: Call, response: okhttp3.Response) {
//				val body = response.body!!.string()
//				val parser = Parser(body)
//				val replyModels = parser.getUserReplies()
//
//				if (totalPage == 0) {
//					totalPage = parser.getTotalPageInMember()
//					mScrollListener?.totalPage = totalPage
//				}
//				activity?.runOnUiThread {
//					if (replyModels.isEmpty()) {
//						if (page == 1) {
//							flcontainer.showNoContent()
//						}
//					} else {
//						if (page == 1) {
//							adapter.updateItem(replyModels)
//						} else {
//							mScrollListener?.success()
//							adapter.addItems(replyModels)
//						}
//						XLog.tag("__REPLY").i(replyModels[0].topic.title)
//					}
//					swipeRefreshLayout.isRefreshing = false
//				}
//				mScrollListener?.loading = false
//			}
//		})
//	}


}