package com.journey.android.v2ex.module.profile

import com.journey.android.v2ex.base.BaseRepository
import com.journey.android.v2ex.model.api.MemberBean
import com.journey.android.v2ex.net.RetrofitService
import com.journey.android.v2ex.room.AppDatabase
import kotlinx.coroutines.flow.Flow
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

}