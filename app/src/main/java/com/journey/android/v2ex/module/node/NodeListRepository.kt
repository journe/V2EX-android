package com.journey.android.v2ex.module.node

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.journey.android.v2ex.base.BaseRepository
import com.journey.android.v2ex.net.RetrofitService
import com.journey.android.v2ex.room.AppDatabase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NodeListRepository @Inject constructor(
	private val db: AppDatabase,
	private val apiService: RetrofitService
) : BaseRepository() {


	fun requestDataLocal() =
		Pager(PagingConfig(pageSize = 20)) {
			db.nodeListDao().pagingSource()
		}.flow

	suspend fun refresh() {
		val list = apiService.getNodesAll()
		db.runInTransaction {
			db.nodeListDao().deleteAll()
			db.nodeListDao().insert(list)
		}
	}

}