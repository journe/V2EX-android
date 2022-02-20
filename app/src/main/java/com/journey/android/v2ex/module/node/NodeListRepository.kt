package com.journey.android.v2ex.module.node

import androidx.paging.PagingSource
import com.journey.android.v2ex.base.BaseRepository
import com.journey.android.v2ex.model.api.NodeBean
import com.journey.android.v2ex.net.RetrofitService
import com.journey.android.v2ex.room.AppDatabase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NodeListRepository @Inject constructor(
	private val db: AppDatabase,
	private val apiService: RetrofitService
) : BaseRepository() {

	suspend fun requestDataLocal(): PagingSource<Int, NodeBean> {
		return db.nodeListDao().pagingSource()
	}

	suspend fun refresh() {
		val list = apiService.getNodesAll()
		db.runInTransaction {
			db.nodeListDao().deleteAll()
			db.nodeListDao().insert(list)
		}
	}

}